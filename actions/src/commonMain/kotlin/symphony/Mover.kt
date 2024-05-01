@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import koncurrent.Later
import kotlinx.JsExport

interface Mover {
    fun at(index: Int): Later<Any>

    fun before(name: String): Later<Any>

    fun after(name: String): Later<Any>
}