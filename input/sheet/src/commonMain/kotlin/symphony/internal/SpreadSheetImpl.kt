package symphony.internal

import cinematic.mutableLiveOf
import kollections.List
import kollections.add
import kollections.addAll
import kollections.any
import kollections.associateWith
import kollections.buildMap
import kollections.emptyList
import kollections.filterIsInstance
import kollections.flatMap
import kollections.forEach
import kollections.getValue
import kollections.map
import kollections.mutableListOf
import kollections.put
import kollections.toMutableMap
import kollections.values
import symphony.CellGetter
import symphony.Changer
import symphony.ComputedRowTemplate
import symphony.RowTemplate
import symphony.SheetCell
import symphony.SheetCells
import symphony.SheetColumn
import symphony.SheetRow
import symphony.SheetState
import symphony.SpreadSheet

@PublishedApi
internal class SpreadSheetImpl(
    template: List<RowTemplate> = emptyList(),
    columns: List<String> = emptyList(),
    private val onChange: Changer<SheetCells>? = null,
) : SpreadSheet {

    private val capturedRows = template.flatMap { it.flatten() }

    private val initial by lazy {
        val rws = capturedRows.map { it.label }
        SheetState(
            structure = template,
            columns = columns,
            rows = rws,
            cells = buildMap {
                capturedRows.forEach { row ->
                    val value = columns.associateWith { col ->
                        if (row is ComputedRowTemplate) {
                            ComputedSheetCellImpl(col, row.label, row.updater)
                        } else {
                            CapturedSheetCellImpl(col, row.label)
                        }
                    }
                    put(row.label, value)
                }
            },
            selected = null
        )
    }

    private fun RowTemplate.flatten(): List<RowTemplate> {
        val list = mutableListOf<RowTemplate>()
        list.add(this)
        asGroupedRowField?.children?.forEach {
            list.addAll(it.flatten())
        }
        return list
    }

    inner class CapturedSheetCellImpl(
        override val column: SheetColumn,
        override val row: SheetRow,
        initial: Double = 0.0
    ) : SheetCell {
        override var value: Double = initial
            set(value) {
                val cells = state.value.cells.toMutableMap()
                val cols = cells.getValue(row).toMutableMap()
                cols.put(column, copy(value))
                cells.put(row, cols)
                state.value = state.value.copy(cells = cells)
                cells.values.forEach { row ->
                    row.values.filterIsInstance<ComputedSheetCellImpl>().forEach {
                        val cell = cells.getValue(it.row).getValue(it.column) as ComputedSheetCellImpl
                        cell.value = try {
                            it.updater { r -> cell(cell.column, r).value }
                        } catch (_: Throwable) {
                            0.0
                        }
                    }
                }
                state.value = state.value.copy(cells = cells)
                onChange?.invoke(cells)
                field = value
            }

        private fun copy(value: Double) = CapturedSheetCellImpl(column, row, value)
    }

    inner class ComputedSheetCellImpl(
        override val column: SheetColumn,
        override val row: SheetRow,
        internal val updater: (CellGetter) -> Double,
        override var value: Double = 0.0
    ) : SheetCell

    override val state = mutableLiveOf(initial)

    override fun cell(column: String, row: String): SheetCell {
        if (!state.value.columns.any { it == column } || !state.value.rows.any { it == row }) {
            throw IllegalArgumentException("Cell ${column}${row} does not exist in the spreadsheet")
        }
        return state.value.cells.getValue(row).getValue(column)
    }

    override fun select(cell: SheetCell) {
        state.value = state.value.copy(selected = cell)
    }
}