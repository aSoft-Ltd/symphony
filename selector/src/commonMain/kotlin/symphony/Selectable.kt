@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface Selectable<T> {
    val selector: SelectionManager<T>
}