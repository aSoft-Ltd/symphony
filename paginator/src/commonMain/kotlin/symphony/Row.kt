@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.JsExport

interface Row<out D> {
    val index: Int
    val item: D
    val number: Int
}