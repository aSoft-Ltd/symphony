@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.mutableLiveOf
import kase.Failure
import kase.FormState
import kase.Pending
import kase.Success
import kase.Validating
import kevlar.Action1Invoker
import koncurrent.Later
import koncurrent.later.finally
import neat.Validator
import neat.Validators
import neat.Validity
import neat.aggregate
import neat.custom
import symphony.properties.Clearable
import symphony.properties.Finishable
import symphony.properties.Resetable
import symphony.properties.Validable
import kotlin.js.JsExport

open class FormField<out F : Fields<P>, P : Any, R>(
    open val heading: String,
    open val details: String,
    open val fields: F,
    validator: (Validators<P>.() -> Validator<P>)?,
    val config: FormConfig,
    initializer: FormInitializer<P, R>,
) : Field<FormState<R>>, Validable, Resetable, Clearable, Finishable {

    private val logger by config.logger

    val validator by lazy { custom<P>(heading).configure(validator) }

    override val state = mutableLiveOf<FormState<R>>(Pending)

    private val actions = FormActionsBuilder<P, R>().apply { initializer() }

    val output: P get() = fields.output

    private val cancelAction = actions.getOrSet("Cancel") {
        logger.warn("Cancel action was never setup")
    }

    private val submitAction: Action1Invoker<P, Later<R>> = actions.submitAction

    val exitOnSubmitted get() = config.exitOnSubmitted

    fun cancel() = try {
        fields.finish()
        cancelAction.asInvoker?.invoke()
    } catch (err: Throwable) {
        state.value = Failure(err)
    }

    fun exit() {
        finish()
        state.value = Pending
    }

    override fun validate(): Validity<P> {
        val fieldResults = fields.validate()
        val formResults = validator.validate(output)
        return when (val res = listOf(fieldResults, formResults).aggregate(output)) {
            is neat.Valid -> res
            is neat.Invalid -> {
                val exception = res.exception()
                state.value = showError(exception)
                logger.error(exception.message)
                res.reasons.forEach { logger.error(it) }
                return res
            }
        }
    }

    override fun validateToErrors() = validate()

    override fun clear() {
        fields.clear()
        state.value = Pending
        state.history.clear()
    }

    override fun reset() {
        fields.reset()
    }

    override fun finish() {
        fields.finish()
        state.stopAll()
    }

    fun submit(): Later<R> = Later(Unit).then {
        logger.info("Validating")
        state.value = Validating
        validate().getOrThrow()
    }.andThen {
        logger.info("Submitting")
        val output = fields.output
        submitAction.invoke(output).finally { res ->
            state.value = when (res) {
                is Failure -> showError(res.cause)
                is Success -> Success(res.data)
            }
            if (res is Success) {
                logger.info("Success")
                state.value = Success(res.data)
                try {
                    actions.onSuccess?.invoke()
                } catch (err: Throwable) {
                    logger.error("Post Submit failed", err)
                }
                if (exitOnSubmitted) exit()
            }
        }
    }

    private fun showError(err: Throwable): Failure<R> {
        logger.error("Failure")
        logger.error(err)
        logger.error(err.stackTraceToString())
        return Failure(err) { onRetry { submit() } }
    }
}