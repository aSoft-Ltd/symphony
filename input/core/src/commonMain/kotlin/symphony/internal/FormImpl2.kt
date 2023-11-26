package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kase.Failure
import kase.Success
import kollections.iListOf
import kollections.toIList
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.finally
import neat.Invalid
import neat.Validator
import neat.custom
import symphony.CapturingPhase
import symphony.FailurePhase
import symphony.Fields
import symphony.Form
import symphony.FormState
import symphony.SubmittingPhase
import symphony.SuccessPhase
import symphony.ValidatingPhase
import symphony.Visibility
import symphony.properties.Clearable
import symphony.properties.Resetable

@PublishedApi
internal class FormImpl2<R, O : Any, F : Fields<O>>(
    private val options: FormOptions<R,O,F>
) : AbstractHideable(), Form<R, O, F>, Resetable, Clearable {

    override val heading = options.heading
    override val details = options.details
    override val fields = options.fields
    override fun submit(): Later<R> {
        logger.info("Validating")
        state.value = state.value.copy(phase = ValidatingPhase(fields.output))
        val validity = fields.validateToErrors()
        if (validity is Invalid) {
            state.value = state.value.copy(phase = FailurePhase(fields.output, validity.reasons.toIList()))
            return FailedLater(validity.exception())
        }

        logger.info("Submitting")
        val output = fields.output
        state.value = state.value.copy(phase = SubmittingPhase(output))
        return submitAction.invoke(output).finally { res ->
            val phase = when (res) {
                is Success -> {
                    logger.info("Success")
                    try {
                        actions.onSuccess?.invoke(res.data)
                    } catch (err: Throwable) {
                        logger.error("Post Submit failed", err)
                    }
                    SuccessPhase(output, res.data)
                }

                is Failure -> {
                    logger.error("Failed",res.cause)
                    try {
                        actions.onFailure?.invoke(res.cause)
                    } catch (err: Throwable) {
                        logger.error("Post Submit failed", err)
                    }
                    FailurePhase(output, iListOf(res.message))
                }
            }
            state.value = state.value.copy(phase = phase)
        }
    }

    private val label by lazy {
        "${fields::class.simpleName}"
    }

    private val actions = options.actions

    private val validator: Validator<O> = custom<O>(label).configure(actions.factory)

    private val logger = options.logger.get(label)

    private val initial = FormState<O, R>(
        visibility = options.visibility,
        phase = CapturingPhase
    )

    override val state: MutableLive<FormState<O, R>> = mutableLiveOf(initial)

    private val cancelAction = actions.getOrSet("Cancel") {
        logger.warn("Cancel action was never setup")
    }

    private val submitAction = actions.submitAction

    val exitOnSubmitted get() = options.exitOnSuccess

    override fun exit() {
        cancelAction.asInvoker?.invoke()
        fields.finish()
        state.stopAll()
        state.history.clear()
    }

    override val visibility get() = state.value.visibility

    override fun setVisibility(v: Visibility) {
        state.value = state.value.copy(visibility = v)
    }

    override fun clear() {
        fields.clear()
        state.value = state.value.copy(phase = CapturingPhase)
    }

    override fun reset() {
        fields.reset()
        state.value = state.value.copy(phase = CapturingPhase)
    }
}