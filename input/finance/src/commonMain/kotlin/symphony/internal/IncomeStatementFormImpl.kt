package symphony.internal

import cinematic.mutableLiveOf
import symphony.IncomeStatementForm

@PublishedApi
internal class IncomeStatementFormImpl : IncomeStatementForm {
    override val revenue = DynamicReportRowImpl(
        title = "Revenue",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }

    override val cogs = DynamicReportRowImpl(
        title = "Cost of Goods Sold",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }

    override val grossProfit = mutableLiveOf(0.0)

    override val expenses = DynamicReportRowImpl(
        title = "Expenses",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }

    override val taxes = DynamicReportRowImpl(
        title = "Taxes",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }
}