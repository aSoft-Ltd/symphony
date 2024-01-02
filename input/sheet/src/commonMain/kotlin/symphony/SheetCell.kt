@file:JsExport

package symphony

import kotlinx.JsExport

interface SheetCell {
    val column: SheetColumn
    val row: SheetRow
    var value: Double
}