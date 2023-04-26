@file:JsExport

package presenters.properties

import kotlin.js.JsExport
import kotlin.js.JsName

@Deprecated("use symphony")
interface Settable<in V> {
    @JsName("setValue")
    fun set(value: V?)
}