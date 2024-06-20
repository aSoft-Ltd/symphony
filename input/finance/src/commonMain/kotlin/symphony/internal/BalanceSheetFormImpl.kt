package symphony.internal

import krono.LocalDateField
import symphony.BalanceSheetForm

@PublishedApi
internal class BalanceSheetFormImpl : BalanceSheetForm {

    override val date = LocalDateField(name = "End Date")

    override val assets = DynamicReportRowImpl(
        title = "Assets",
        removable = false
    ).apply {
        expand()
        add("Current Assets", false)
        add("Fixed Assets", false)
    }

    override val equity = DynamicReportRowImpl(
        title = "Equity",
        removable = false
    ).apply {
        expand()
    }

    override val liabilities = DynamicReportRowImpl(
        title = "Liabilities",
        removable = false
    ).apply {
        expand()
        add("Current Liabilities", false)
        add("Long Term Liabilities", false)
    }
}