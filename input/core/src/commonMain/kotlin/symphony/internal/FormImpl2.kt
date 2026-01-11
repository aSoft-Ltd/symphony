package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kotlinx.coroutines.CancellationException
import neat.Invalid
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

    private fun Fields<O>.errors() = all.entries.filter { (_, it) ->
        it.state.value.feedbacks.errors.isNotEmpty()
    }.map { (prop, it) ->
        val s = it.state.value
        FailurePhase.FieldError(name = prop, s.feedbacks.errors)
    }

    override suspend fun submit() {
        logger.info("Validating")
        state.value = state.value.copy(phase = ValidatingPhase(fields.output))
        val validity = fields.validateToErrors()
        if (validity is Invalid) {
            val phase = FailurePhase(fields.output, emptyList(), fields.errors())
            state.value = state.value.copy(phase = phase)
            handers.failure.forEach { it.invoke(phase) }
            return
        }

        logger.info("Submitting")
        val output = fields.output
        state.value = state.value.copy(phase = SubmittingPhase(output))
        val phase = try {
            val res = submitAction.invoke(output)
            logger.info("Success")
            try {
                this.acts.onSuccess?.invoke(res)
                handers.success.forEach { it.invoke(res) }
            } catch (err: Throwable) {
                logger.error("Post Submit failed", err)
            }
            SuccessPhase(output, res)
        } catch (err: CancellationException) {
            throw err
        } catch (err: Throwable) {
            val f = FailurePhase(output, listOf(err.message ?: "Unknown error"), fields.errors())
            logger.info("Failure: ${f.reasons.joinToString(prefix = "\n", separator = "\n")}")
            try {
                acts.onFailure?.invoke(err)
                handers.failure.forEach { it.invoke(f) }
            } catch (err: Throwable) {
                logger.error("Post Submit failed", err)
            }
            f
        }
        state.value = state.value.copy(phase = phase)
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
        unsubscribe()
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

    override fun result(): R = when (val p = state.value.phase) {
        is CapturingPhase -> throw IllegalStateException("Form is not submitted yet")
        is ValidatingPhase -> throw IllegalStateException("Form is still validating")
        is SubmittingPhase -> throw IllegalStateException("Form is still submitting")
        is FailurePhase -> throw p.toException()
        is SuccessPhase -> p.result
    }

    private val handers by lazy { Handlers<R, O>(mutableListOf(), mutableListOf()) }

    class Handlers<R, O>(
        val success: MutableList<(R) -> Unit>,
        val failure: MutableList<(FailurePhase<O>) -> Unit>
    ) {
        fun clear() {
            success.clear()
            failure.clear()
        }
    }

    override fun onFailure(handler: (FailurePhase<O>) -> Unit) {
        handers.failure.add(handler)
    }

    override fun onSuccess(handler: (R) -> Unit) {
        handers.success.add(handler)
    }

    override fun unsubscribe() {
        handers.clear()
    }
}