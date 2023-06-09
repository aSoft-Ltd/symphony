@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.properties

import symphony.Visibility
import kotlin.js.JsExport

interface Hideable {
    val visibility: Visibility
    fun show(show: Boolean? = true)
    fun hide(hide: Boolean? = true)
    fun setVisibility(v: Visibility)
}