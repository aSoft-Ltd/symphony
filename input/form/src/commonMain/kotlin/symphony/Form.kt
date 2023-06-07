@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Scene
import kase.Failure
import kase.FormState
import kase.Pending
import kase.Submitting
import kase.Success
import kase.Validating
import kevlar.Action1Invoker
import kevlar.action0
import kollections.toIList
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.finally
import kotlin.js.JsExport
import symphony.exceptions.FormValidationException
import symphony.properties.Labeled
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.Validateable
import symphony.validation.ValidationResult
import symphony.validation.throwIfInvalid

open class Form<out F : Fields, out P, out R>(
    open val heading: String,
    open val details: String,
    open val fields: F,
    val config: FormConfig<@UnsafeVariance P>,
    initializer: FormActionsBuildingBlock<P, R>,
) : Scene<FormState<R>>(Pending) {

    private val logger = config.logger.with("source" to this::class.simpleName)
    private val builtActions = FormActionsBuilder<P, R>().apply { initializer() }

    val cancelAction = action0("Cancel") {
        val handler = builtActions.actions.firstOrNull {
            it.name.contentEquals("Cancel", ignoreCase = true)
        }?.asInvoker?.handler ?: { config.logger.warn("Cancel action of ${this::class.simpleName} was never setup") }
        handler()
    }

    private val submitAction: Action1Invoker<P, Later<R>> = builtActions.submitAction

    private val codec get() = config.codec

    val exitOnSubmitted get() = config.exitOnSubmitted

    fun cancel() = try {
        fields.stopAllWatchers()
        cancelAction.invoke()
    } catch (err: Throwable) {
        ui.value = Failure(err)
    }

    fun exit() {
        ui.value = Pending
        cancel()
    }

    private fun Collection<SerializableLiveData<out Any?>>.errorTable() = simpleTableOf(this) {
        column("Field") {
            (it.item as? Labeled)?.label?.capitalizedWithAstrix() ?: it.item.name
        }
        column("Value") {
            it.item.data.value.output.toString()
        }
        column("Reason") {
            (it.item as? Validateable<Any?>)?.feedback?.value?.asError?.message ?: "Unknown"
        }
    }.renderToString()

    fun validate(): ValidationResult {
        val invalids = fields.validate()

        if (invalids.isEmpty()) return Valid

        val size = invalids.size
        val terminator = "input" + if (size > 1) "s" else ""
        val exception = FormValidationException(
            message = "You have $size invalid $terminator",
            errors = invalids.errorTable(),
            fields = invalids.toIList()
        )
        logger.error(exception.message)
        logger.error(exception.errors)
        return Invalid(exception)
    }

    fun clear() {
        fields.clearAll()
        ui.value = Pending
        ui.history.clear()
    }

    fun submit(): Later<R> = try {
        logger.info("Validating")
        ui.value = Validating
        validate().throwIfInvalid()
        val values = fields.encodedValuesToJson(codec)
        logger.info("Submitting")
        ui.value = Submitting(values)
        submitAction.invoke(codec.decodeFromString(config.serializer, values)).finally { res ->
            ui.value = when (res) {
                is Failure -> showError(res.cause)
                is Success -> Success(res.data)
            }
            if (res is Success) {
                logger.info("Success")
                if (exitOnSubmitted) exit()
            }
        }
    } catch (err: Throwable) {
        val e = showError(err)
        ui.value = e
        FailedLater(e.cause)
    }

    private fun showError(err: Throwable): Failure<R> {
        logger.error("Failure")
        logger.error(err)
        logger.error(err.stackTraceToString())
        return Failure(err) { onRetry { submit() } }
    }
}