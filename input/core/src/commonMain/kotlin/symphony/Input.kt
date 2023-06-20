@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import neat.Validator
import neat.Validity
import neat.aggregate
import neat.custom
import kotlin.js.JsExport

open class Input<out F : Fields<*>>(val fields: F) : Field<Fields.State<*>> {
    override val state: Live<Fields.State<*>> get() = fields.state
    val validator: Validator<Any?> = custom<Any?>("").optional()

    override fun finish() {
        fields.finish()
    }

    override fun validate(): Validity<*> {
        val output = fields.output
        val fieldResults = fields.validate()
        val inputResults = validator.validate(output)
        return listOf(fieldResults, inputResults).aggregate(output)
    }

    override fun validateToErrors(): Validity<*> = fields.validateToErrors()

    override fun clear() {
        fields.clear()
    }

    override fun reset() {
        fields.reset()
    }

    override val hidden: Boolean get() = fields.hidden
    override fun hide(hide: Boolean?) = fields.hide(hide)
    override fun show(show: Boolean?) = fields.show(show)
}