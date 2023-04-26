@file:JsExport

package presenters.properties

import kotlin.js.JsExport

@Deprecated("use symphony")
interface SettableRange<in V> {
    fun setStart(value: V?)
    fun setEnd(value: V?)
}