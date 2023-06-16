@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.MutableLive
import cinematic.Scene
import cinematic.mutableLiveOf
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
import symphony.exceptions.FormValidationException
import symphony.internal.OutputData
import symphony.properties.Clearable
import symphony.properties.Labeled
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.Validateable
import symphony.validation.ValidationResult
import symphony.validation.throwIfInvalid
import kotlin.js.JsExport

open class Form<out F : Fields<P>, out P : Any, out R>(
    open val heading: String,
    open val details: String,
    open val fields: F,
    val config: FormConfig,
    initializer: FormActionsBuildingBlock<P, R>,
) : Scene<FormState<R>>(Pending), Validateable<@UnsafeVariance P>, Clearable {

    private val logger = config.logger.with("source" to this::class.simpleName)
    private val builtActions = FormActionsBuilder<P, R>().apply { initializer() }

    override val name: String by lazy { heading.replace(" ", "") }
    override val feedback = mutableLiveOf<InputFieldState>(InputFieldState.Empty)
    override val output: P get() = fields.output
    override val data: MutableLive<Data<@UnsafeVariance P>> by lazy { mutableLiveOf(OutputData(fields.output)) }

    val cancelAction = action0("Cancel") {
        val handler = builtActions.actions.firstOrNull {
            it.name.contentEquals("Cancel", ignoreCase = true)
        }?.asInvoker?.handler ?: { config.logger.warn("Cancel action of ${this::class.simpleName} was never setup") }
        handler()
    }

    private val submitAction: Action1Invoker<P, Later<R>> = builtActions.submitAction

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

    private fun Collection<Validateable<out Any?>>.errorTable() = simpleTableOf(this) {
        column("Field") {
            (it.item as? Labeled)?.label?.capitalizedWithAstrix() ?: it.item.name
        }
        column("Value") {
            it.item.data.value.output.toString()
        }
        column("Reason") {
            it.item.feedback.value.asError?.message ?: "Unknown"
        }
    }.renderToString()


    override fun validate(value: @UnsafeVariance P?): ValidationResult {
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

    override fun validateSettingInvalidsAsErrors(value: @UnsafeVariance P?): ValidationResult {
        return validate(value)
    }

    override fun validateSettingInvalidsAsWarnings(value: @UnsafeVariance P?): ValidationResult {
        return validate(value)
    }

    override fun clear() {
        fields.clearAll()
        ui.value = Pending
        ui.history.clear()
    }

    fun submit(): Later<R> = try {
        logger.info("Validating")
        ui.value = Validating
        validate().throwIfInvalid()
        logger.info("Submitting")
        val output = fields.output
        ui.value = Submitting(output.toString())
        submitAction.invoke(output).finally { res ->
            ui.value = when (res) {
                is Failure -> showError(res.cause)
                is Success -> Success(res.data)
            }
            if (res is Success) {
                logger.info("Success")
                data.value = OutputData(output)
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