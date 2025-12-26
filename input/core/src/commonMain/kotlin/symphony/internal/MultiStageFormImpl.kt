@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "UNCHECKED_CAST")

package symphony.internal

import cinematic.mutableLiveOf
import kase.Failure
import kase.Success
import kotlinx.JsExport
import neat.Invalid
import neat.Validator
import neat.custom
import symphony.CapturingPhase
import symphony.FailurePhase
import symphony.FormStage
import symphony.MultiStageForm
import symphony.MultiStageFormStageState
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
internal class MultiStageFormImpl<R : Any, O : Any, S : FormStage>(
    val output: O,
    override val stages: List<S>,
    visibility: Visibility,
    private val config: SubmitConfig,
    builder: SubmitBuilder<O, R>,
) : AbstractHideable(), MultiStageForm<R, O, S>, Resetable, Clearable {

    override suspend fun prev() {
        if (state.value.stage.isFirst) return
        state.value.stage.current.onPrev?.invoke()
        val prev = state.value.prev()
        state.value = prev
        return
    }

    override suspend fun next() {
        state.value.stage.current.onNext?.invoke()
        val stage = state.value.stage
        if (stage.isLast) return submit()
        val validity = stage.current.fields.validateToErrors()
        if (validity is Invalid) return TODO("Find a way to surface erros") // validity.exception()
        val next = state.value.next()
        state.value = next
        return Later(next)
    }

    private fun submit(): Later<R> {
        logger.info("Validating")
        val stage = state.value.stage.current
        state.value = state.value.copy(phase = ValidatingPhase(output))
        val validity = stage.fields.validateToErrors()
        if (validity is Invalid) return FailedLater(validity.exception())

        logger.info("Submitting")
        state.value = state.value.copy(phase = SubmittingPhase(output))
        return submitAction.invoke(output).finally { res ->
            val phase = when (res) {
                is Failure -> FailurePhase(output, listOf(res.message))
                is Success -> SuccessPhase(output, res.data)
            }
            state.value = state.value.copy(phase = phase)
            if (res is Success) {
                logger.info("Success")
                try {
                    prerequisites.onSuccess?.invoke(res.data)
                } catch (err: Throwable) {
                    logger.error("Post Submit failed", err)
                }
            }
        }
    }

    private val label by lazy {
        "${stages::class.simpleName}Form"
    }

    private val prerequisites = SubmitActionsBuilder<O, R>().apply { builder() }

    private val validator: Validator<O> = custom<O>(label).configure(prerequisites.factory)

    val logger by lazy { config.logger.with("source" to label) }

    private val actions = SubmitActionsBuilder<O, R>().apply { builder() }

    private val cancelAction = actions.getOrSet("Cancel") {
        logger.warn("Cancel action was never setup")
    }

    private val submitAction = prerequisites.submitAction

    val exitOnSubmitted get() = config.exitOnSuccess

    override fun exit() {
        cancelAction.asInvoker?.invoke()
        stages.forEach { it.fields.finish() }
        state.stopAll()
        state.history.clear()
    }

    override fun setVisibility(v: Visibility) {
        state.value = state.value.copy(visibility = v)
    }

    override fun clear() {
        stages.forEach { it.fields.clear() }
        state.value = state.value.copy(phase = CapturingPhase)
    }

    override fun reset() {
        stages.forEach { it.fields.reset() }
        state.value = state.value.copy(phase = CapturingPhase)
    }


    private val initial = MultiStageFormStageState<R, O, S>(
        visibility = visibility,
        stages = stages,
        output = output,
        phase = CapturingPhase
    )

    override val state = mutableLiveOf(initial)
}