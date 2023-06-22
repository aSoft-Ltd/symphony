@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import krono.LocalDate
import kotlin.js.JsExport

interface DateField<D : LocalDate?> : BaseField<D> {
    fun set(iso: String)
}