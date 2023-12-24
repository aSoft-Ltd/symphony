package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kase.Failure
import kase.Success
import kollections.listOf
import kollections.toList
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import koncurrent.later.finally
import neat.Invalid
import neat.Validator
import neat.custom
import symphony.CapturingPhase
import symphony.FailurePhase
import symphony.Fields
import symphony.Form
import symphony.FormAction
import symphony.FormActions
import symphony.FormState
import symphony.Label
import symphony.SubmittingPhase
import symphony.SuccessPhase
import symphony.ValidatingPhase
import symphony.Visibility
import symphony.properties.Clearable
import symphony.properties.Resetable

@PublishedApi
internal class FormImpl2<R, O : Any, F : Fields<O>>(
    private val options: FormOptions<R, O, F>
) : AbstractHideable(), Form<R, O, F>, Resetable, Clearable {

    override val heading = options.heading
    override val details = options.details
    override val fields = options.fields
    override fun submit(): Later<R> {
        logger.info("Validating")
        state.value = state.value.copy(phase = ValidatingPhase(fields.output))
        val validity = fields.validateToErrors()
        if (validity is Invalid) {
            state.value = state.value.copy(phase = FailurePhase(fields.output, validity.reasons.toList()))
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
                        this.acts.onSuccess?.invoke(res.data)
                    } catch (err: Throwable) {
                        logger.error("Post Submit failed", err)
                    }
                    SuccessPhase(output, res.data)
                }

                is Failure -> {
                    logger.error("Failed", res.cause)
                    try {
                        this.acts.onFailure?.invoke(res.cause)
                    } catch (err: Throwable) {
                        logger.error("Post Submit failed", err)
                    }
                    FailurePhase(output, listOf(res.message))
                }
            }
            state.value = state.value.copy(phase = phase)
        }
    }

    private val label by lazy {
        "${fields::class.simpleName}"
    }

    override val actions by lazy {
        val cancel = acts.getOrSet("Cancel") { logger.warn("Cancel action was never setup") }
        val sub = acts.submitAction
        FormActions(
            cancel = FormAction(label = Label(cancel.name, false), handler = { exit() }),
            submit = FormAction(label = Label(sub.name, false), handler = { submit() })
        )
    }

    private val acts = options.actions

    private val logger = options.logger.get(label)

    private val initial = FormState<O, R>(
        visibility = options.visibility,
        phase = CapturingPhase
    )

    override val state: MutableLive<FormState<O, R>> = mutableLiveOf(initial)

    private val cancelAction = acts.getOrSet("Cancel") {
        logger.warn("Cancel action was never setup")
    }

    private val submitAction = acts.submitAction

    override fun exit() {
        cancelAction.asInvoker?.invoke()
        fields.finish()
        state.stopAll()
        state.history.clear()
    }

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