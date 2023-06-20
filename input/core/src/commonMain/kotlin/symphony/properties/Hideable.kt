@file:JsExport

package symphony.properties

import kotlin.js.JsExport

interface Hideable {
    val hidden: Boolean
    fun show(show: Boolean? = true)
    fun hide(hide: Boolean? = true)
}