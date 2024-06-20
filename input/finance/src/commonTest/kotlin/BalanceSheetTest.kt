import kommander.expect
import symphony.BalanceSheetForm
import kotlin.test.Test

class BalanceSheetTest {

    private val sheet = BalanceSheetForm()

    @Test
    fun should_be_able_to_create_an_empty_sheet() {
        val assets = sheet.assets.total.output
        expect(assets).toBe(0.0)

        val equity = sheet.equity.total.state.value.output
        expect(equity).toBe(0.0)

        val liabilities = sheet.liabilities.total.output
        expect(liabilities).toBe(0.0)

        println(sheet.renderToString())
    }

    @Test
    fun should_be_able_to_display_rows() {
        val cassets = sheet.assets
    }
}