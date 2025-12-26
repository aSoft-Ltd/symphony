@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface Mover {
    suspend fun at(index: Int)

    suspend fun before(name: String)

    suspend fun after(name: String)
}