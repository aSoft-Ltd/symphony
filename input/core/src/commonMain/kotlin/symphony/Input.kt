@file:JsExport @file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.mutableLiveOf
import kase.Failure
import kase.Success
import kollections.iListOf
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.finally
import neat.Invalid
import neat.Valid
import neat.Validator
import neat.Validity
import neat.aggregate
import neat.custom
import kotlin.js.JsExport

open class Input<out F : Fields<*>>(
    val fields: F,
    hidden: Boolean = false,
    val config: SubmitConfig = SubmitConfig(),
    val builder: SubmitBuilder<Any?, Any?> = { onSuccess { config.logger.log("Success") } }
) : Field<InputState<Any?, Any?>> {

    private val label = if (this::class == Input::class) {
        fields::class.simpleName
    } else {
        this::class.simpleName
    } + "Input"

    val prerequisites = SubmitActionsBuilder<Any?, Any?>().apply { builder() }
    val validator: Validator<Any?> = custom<Any?>(label).configure(prerequisites.factory)

    val logger by lazy { config.logger.with("source" to label) }

    val submitAction = prerequisites.submitAction

    override fun validate(): Validity<*> {
        val output = fields.output
        val fieldResults = fields.validate()
        val inputResults = validator.validate(output)
        return listOf(fieldResults, inputResults).aggregate(output)
    }

    fun submit(): Later<Any?> {
        logger.info("Validating")
        state.value = state.value.copy(phase = ValidatingPhase(fields.output))
        val validity = validateToErrors()
        if (validity is Invalid) return FailedLater(validity.exception())

        logger.info("Submitting")
        val output = fields.output
        state.value = state.value.copy(phase = SubmittingPhase(output))
        return submitAction.invoke(output).finally { res ->
            val phase = when (res) {
                is Failure -> FailurePhase(output, iListOf(res.message))
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

    override fun validateToErrors(): Validity<*> = when (val res = validate()) {
        is Valid -> res
        is Invalid -> {
            val exception = res.exception()
            logger.error(exception.message)
            logger.error(exception.details)
            state.value = state.value.copy()
            res
        }
    }

    override fun clear() {
        fields.clear()
        state.history.clear()
    }

    override fun reset() {
        fields.reset()
    }

    private val initial = InputState<Any?, Any?>(
        hidden = hidden,
        phase = CapturingPhase
    )

    final override val state = mutableLiveOf(initial)

    override val hidden: Boolean get() = state.value.hidden

    override fun hide(hide: Boolean?) {
        state.value = state.value.copy(hidden = hide == true)
        fields.hide(hide)
    }

    override fun show(show: Boolean?) {
        state.value = state.value.copy(hidden = show == false)
        fields.show(show)
    }

    override fun finish() {
        fields.finish()
    }
}