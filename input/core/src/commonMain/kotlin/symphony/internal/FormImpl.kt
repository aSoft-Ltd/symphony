package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kase.Failure
import kase.Success
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
import symphony.SubmitActionsBuilder
import symphony.SubmitBuilder
import symphony.SubmitConfig
import symphony.SubmittingPhase
import symphony.SuccessPhase
import symphony.ValidatingPhase
import symphony.Visibility
import symphony.properties.Clearable
import symphony.properties.Resetable

@PublishedApi
internal class FormImpl<R, O : Any, F : Fields<O>>(
    override val heading: String,
    override val details: String,
    override val fields: F,
    private val config: SubmitConfig,
    visibility: Visibility,
    builder: SubmitBuilder<O, R>,
) : AbstractHideable(), Form<R, O, F>, Resetable, Clearable {

    override suspend fun submit() {
        TODO("Do proper form phase here")
//        logger.info("Validating")
//        state.value = state.value.copy(phase = ValidatingPhase(fields.output))
//        val validity = fields.validateToErrors()
//        if (validity is Invalid) {
//            state.value = state.value.copy(phase = FailurePhase(fields.output, validity.reasons.toList()))
//            return TODO() // find a way to return proper form errors
//        }
//
//        logger.info("Submitting")
//        val output = fields.output
//        state.value = state.value.copy(phase = SubmittingPhase(output))
//        val res = submitAction.invoke(output)
//        val phase = when (res) {
//            is Success -> {
//                logger.info("Success")
//                try {
//                    prerequisites.onSuccess?.invoke(res.data)
//                } catch (err: Throwable) {
//                    logger.error("Post Submit failed", err)
//                }
//                SuccessPhase(output, res.data)
//            }
//
//            is Failure -> {
//                logger.info("Success")
//                try {
//                    prerequisites.onFailure?.invoke(res.cause)
//                } catch (err: Throwable) {
//                    logger.error("Post Submit failed", err)
//                }
//                FailurePhase(output, listOf(res.message))
//            }
//        }
//        state.value = state.value.copy(phase = phase)
    }

    private val label by lazy {
        "${fields::class.simpleName}Form"
    }

    private val prerequisites = SubmitActionsBuilder<O, R>().apply { builder() }

    private val validator: Validator<O> = custom<O>(label).configure(prerequisites.factory)

    private val logger by config.logger

    private val initial = FormState<O, R>(
        visibility = visibility,
        phase = CapturingPhase
    )

    override val state: MutableLive<FormState<O, R>> = mutableLiveOf(initial)


    private val acts = SubmitActionsBuilder<O, R>().apply { builder() }

    private val cancelAction = acts.getOrSet("Cancel") {
        logger.warn("Cancel action was never setup")
    }

    private val submitAction = prerequisites.submitAction

    override val actions by lazy {
        val cancel = acts.getOrSet("Cancel") { logger.warn("Cancel action was never setup") }
        val sub = acts.submitAction
        FormActions(
            cancel = FormAction(label = Label(cancel.name, false), handler = { exit() }),
            submit = FormAction(label = Label(sub.name, false), handler = { submit() })
        )
    }

    val exitOnSubmitted get() = config.exitOnSuccess

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