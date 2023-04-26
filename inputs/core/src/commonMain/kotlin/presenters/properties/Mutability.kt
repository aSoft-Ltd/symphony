@file:JsExport

package presenters.properties

import kotlin.js.JsExport

@Deprecated("use symphony")
interface Mutability {
    val isReadonly: Boolean
}