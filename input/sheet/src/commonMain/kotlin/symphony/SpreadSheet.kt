@file:JsExport
package symphony

import cinematic.Live
import kotlinx.JsExport

interface SpreadSheet {
    val state: Live<SheetState>

    fun select(cell: SheetCell)

    fun cell(column: String, row: String): SheetCell

    val output get() = state.value
}