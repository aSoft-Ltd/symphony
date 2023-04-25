@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.collections

import kotlin.js.JsExport

@Deprecated("use symphony instead")
interface Selectable<T> {
    val selector: SelectionManager<T>
}