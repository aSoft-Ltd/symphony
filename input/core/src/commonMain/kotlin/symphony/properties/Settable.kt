@file:JsExport

package symphony.properties

import kotlin.js.JsExport
import kotlin.js.JsName

interface Settable<V> {
    @JsName("setValue")
    fun set(value: V?)
}