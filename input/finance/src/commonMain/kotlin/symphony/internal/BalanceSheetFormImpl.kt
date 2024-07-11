package symphony.internal

//import krono.LocalDateField
import symphony.BalanceSheetForm

@PublishedApi
internal class BalanceSheetFormImpl : BalanceSheetForm {

//    override val date = LocalDateField(name = "End Date")

    override val assets = DynamicReportRowImpl(
        title = "Assets",
        removable = false,
        appendable = false
    ).apply {
        expand()
        add("Current Assets", removable = false, appendable = true)
        add("Fixed Assets", removable = false, appendable = true)
    }

    override val equity = DynamicReportRowImpl(
        title = "Equity",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }

    override val liabilities = DynamicReportRowImpl(
        title = "Liabilities",
        removable = false,
        appendable = false
    ).apply {
        expand()
        add("Current Liabilities", removable = false, appendable = true)
        add("Long Term Liabilities", removable = false, appendable = true)
    }
}