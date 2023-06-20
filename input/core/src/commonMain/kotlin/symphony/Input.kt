@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import neat.Validator
import neat.Validity
import neat.aggregate
import neat.custom
import symphony.properties.Clearable
import symphony.properties.Finishable
import symphony.properties.Resetable
import symphony.properties.Validable
import kotlin.js.JsExport

abstract class Input<out F : Fields<*>>(val fields: F) : Finishable, Clearable, Resetable, Validable {
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

    override fun validateToErrors(): Validity<*> = fields.validate()

    override fun clear() {
        fields.clear()
    }

    override fun reset() {
        fields.reset()
    }
}