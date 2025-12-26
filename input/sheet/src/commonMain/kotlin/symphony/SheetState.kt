@file:JsExport

package symphony

import kotlinx.JsExport

data class SheetState(
    val columns: List<String>,
    val structure: List<RowTemplate>,
    val rows: List<String>,
    val cells: SheetCells,
    val selected: SheetCell?
)