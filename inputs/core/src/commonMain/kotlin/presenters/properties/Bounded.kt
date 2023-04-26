@file:JsExport

package presenters.properties

import kotlin.js.JsExport

@Deprecated("use symphony")
interface Bounded<out B> {
    val max: B?
    val min: B?
}