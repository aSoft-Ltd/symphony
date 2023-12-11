@file:JsExport

package symphony.properties

import kotlinx.JsExport

interface SettableRange<in V> {
    fun setStart(value: V?)
    fun setEnd(value: V?)
}