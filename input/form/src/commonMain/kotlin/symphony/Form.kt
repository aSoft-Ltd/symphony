@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kase.Failure
import kase.FormState
import kase.Pending
import kase.Success
import kase.Validating
import kevlar.Action1Invoker
import koncurrent.Later
import koncurrent.later.finally
import neat.Validity
import symphony.properties.Clearable
import symphony.properties.Finishable
import symphony.properties.Resetable
import kotlin.js.JsExport

open class Form<out O, in P, out F : Fields<@UnsafeVariance P>, out I : Input<F>>(
    open val heading: String,
    open val details: String,
    open val input: I,
    val config: FormConfig,
    initializer: FormInitializer<P, O>,
) : Resetable, Clearable, Finishable {

    private val logger by lazy {
        config.logger.with("source" to "${input::class.simpleName ?: ""}Form")
    }

    val state: MutableLive<FormState<@UnsafeVariance O>> = mutableLiveOf<FormState<O>>(Pending)

    val fields: F get() = input.fields

    private val actions = FormActionsBuilder<P, O>().apply { initializer() }

    private val cancelAction = actions.getOrSet("Cancel") {
        logger.warn("Cancel action was never setup")
    }

    private val submitAction: Action1Invoker<P, Later<O>> = actions.submitAction

    val exitOnSubmitted get() = config.exitOnSubmitted

    fun cancel() = try {
        input.finish()
        cancelAction.asInvoker?.invoke()
    } catch (err: Throwable) {
        state.value = Failure(err)
    }

    fun exit() {
        finish()
        state.value = Pending
    }

    fun validate(): Validity<*> = when (val res = input.validateToErrors()) {
        is neat.Valid -> res
        is neat.Invalid -> {
            val exception = res.exception()
            logger.error(exception.message)
            logger.error(exception.details)
            state.value = showError(exception)
            res
        }
    }

    override fun clear() {
        input.clear()
        state.value = Pending
        state.history.clear()
    }

    override fun reset() = input.reset()

    override fun finish() {
        input.finish()
        state.stopAll()
    }

    fun submit(): Later<O> = Later(Unit).then {
        logger.info("Validating")
        state.value = Validating
        validate().getOrThrow()
    }.andThen {
        logger.info("Submitting")
        submitAction.invoke(input.fields.output).finally { res ->
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

    private fun showError(err: Throwable): Failure<O> {
        logger.error("Failure")
        logger.error(err)
        logger.error(err.stackTraceToString())
        return Failure(err) { onRetry { submit() } }
    }
}