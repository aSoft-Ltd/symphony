@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "UNCHECKED_CAST")

package symphony

import cinematic.MutableLive
import koncurrent.Later
import neat.Validator
import neat.custom
import symphony.properties.Clearable
import symphony.properties.Finishable
import symphony.properties.Hideable
import symphony.properties.Resetable
import kotlin.js.JsExport

open class Form<out R, O : Any, out F : Fields<@UnsafeVariance O>, out I : Input<F>>(
    open val heading: String,
    open val details: String,
    val input: I,
    private val config: SubmitConfig,
    builder: SubmitBuilder<O, R>,
) : Resetable by input, Clearable by input, Finishable by input, Hideable by input {

    private val label by lazy {
        if (input::class == Input::class) {
            fields::class.simpleName
        } else {
            input::class.simpleName
        } + "Form"
    }

    private val prerequisites = SubmitActionsBuilder<O, R>().apply { builder() }

    private val validator: Validator<O> = custom<O>(label).configure(prerequisites.factory)

    val logger by lazy { config.logger.with("source" to label) }

    val state: MutableLive<InputState<O, @UnsafeVariance R>> = input.state as MutableLive<InputState<O, R>>

    val fields: F get() = input.fields

    private val actions = SubmitActionsBuilder<O, R>().apply { builder() }

    private val cancelAction = actions.getOrSet("Cancel") {
        logger.warn("Cancel action was never setup")
    }

    val exitOnSubmitted get() = config.exitOnSuccess

    fun cancel() = try {
        input.finish()
        cancelAction.asInvoker?.invoke()
    } catch (_: Throwable) {

    }

    fun exit() {
        finish()
    }

    fun submit() = input.submit() as Later<R>
}