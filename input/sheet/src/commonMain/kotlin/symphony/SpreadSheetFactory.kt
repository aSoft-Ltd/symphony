package symphony

import kollections.List
import kollections.emptyList
import symphony.internal.SpreadSheetImpl

fun SpreadSheet(
    template: List<RowTemplate> = emptyList(),
    columns: List<String> = emptyList(),
    onChange: Changer<SheetCells>? = null,
) : SpreadSheet = SpreadSheetImpl(template,columns,onChange)