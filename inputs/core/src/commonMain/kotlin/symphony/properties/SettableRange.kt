@file:JsExport

package symphony.properties

import kotlin.js.JsExport

interface SettableRange<in V> {
    fun setStart(value: V?)
    fun setEnd(value: V?)
}