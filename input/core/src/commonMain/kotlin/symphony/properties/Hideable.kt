@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.properties

import symphony.Visibility
import kotlinx.JsExport

interface Hideable {
    fun show(show: Boolean? = true)
    fun hide(hide: Boolean? = true)
    fun setVisibility(v: Visibility)
}