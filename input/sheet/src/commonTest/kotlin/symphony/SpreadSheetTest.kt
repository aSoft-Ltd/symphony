package symphony

import kollections.listOf
import kollections.size
import kommander.expect
import kommander.expectFunction
import kotlin.test.Test

class SpreadSheetTest {
    @Test
    fun should_create_a_field_with_no_rows_nor_column() {
        val sheet = SpreadSheet()
        expect(sheet.output.columns.size).toBe(0)
        expect(sheet.output.rows.size).toBe(0)
    }

    @Test
    fun should_be_able_to_start_with_a_given_structure() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                captured("1")
                captured("2")
                captured("3")
            },
            columns = listOf("A", "B", "C")
        )
        expect(sheet.output.columns.size).toBe(3)
        expect(sheet.output.rows.size).toBe(3)
    }

    @Test
    fun should_be_able_to_enter_info_to_the_cells_of_a_specific_captured_and_column() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                captured("1")
                captured("2")
                captured("3")
            },
            columns = listOf("A", "B", "C")
        )

        sheet.cell("A", "1").value = 200.0
        println(sheet.rendererToString(4))
        expect(sheet.cell("A", "1").value).toBe(200.0)
    }

    @Test
    fun should_be_fail_to_get_rows_that_do_not_exist() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                captured("1")
                captured("2")
                captured("3")
            },
            columns = listOf("A", "B", "C")
        )

        val err = expectFunction { sheet.cell("D", "2") }.toFail()
        expect(err.message).toBe("Cell D2 does not exist in the spreadsheet")
    }

    @Test
    fun should_be_able_to_have_a_structured_template() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                group("Revenue") {
                    captured("Sales")
                    captured("Appreciations")
                }
                captured("COGS")
                group("Expenses") {
                    captured("Depreciation")
                    captured("Amortization")
                    captured("Other expenses")
                }
            },
            columns = listOf("2021", "2022", "2023")
        )

        sheet.cell("2023", "Depreciation").value = 300.0
        expect(sheet.cell("2023", "Depreciation").value).toBe(300.0)
    }

    @Test
    fun should_be_able_to_have_auto_computed_cells() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                captured("Sale")
                captured("COGS")
                computed("Prof") { cell ->
                    cell("Sale") - cell("COGS")
                }
            },
            columns = listOf("2021", "2022", "2023")
        )

        sheet.cell("2021", "Sale").value = 900.0
        sheet.cell("2021", "COGS").value = 200.0
        expect(sheet.cell("2021", "Prof").value).toBe(700.0)
    }

    @Test
    fun should_be_able_to_get_cells_of_the_spreadsheet() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                captured("1")
                captured("2")
            },
            columns = listOf("A", "B")
        )

        sheet.cell("A", "1").value = 100.0
        expect(sheet.cell("A", "1").value).toBe(100.0)
    }

    @Test
    fun should_not_fail_computing_erroneous_fields() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                captured("1")
                captured("2")
                computed("3") { cell ->
                    var zero = 0
                    val x = 1 / zero
                    cell("1") + cell("2") + x
                }
            },
            columns = listOf("A", "B")
        )

        sheet.cell("A", "1").value = 100.0
        expect(sheet.cell("A", "3").value).toBe(0.0)

        sheet.cell("A", "2").value = 100.0
        expect(sheet.cell("A", "3").value).toBe(0.0)
    }

    @Test
    fun should_be_able_to_render_the_cells() {
        val sheet = SpreadSheet(
            template = buildTemplate {
                group("This") {
                    captured("th1s")
                    captured("th2s")
                }
                group("That") {
                    captured("th3t")
                    captured("th4t")
                }
                group("Nested") {
                    group("Inner Nest") {
                        captured("Inner Captured")
                    }
                }
                computed("Sum ") { cell ->
                    cell("th1s") + cell("th2s") + cell("th3t") + cell("th4t")
                }
            },
            columns = listOf("A", "B", "C", "D")
        )

        println(sheet.rendererToString(4))
    }
}