package symphony.internal

import symphony.CashFlowStatementForm

@PublishedApi
internal class CashFlowStatementFormImpl: CashFlowStatementForm {
    override val operating = DynamicReportRowImpl(
        title = "Cash from Operating Activities",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }

    override val investment = DynamicReportRowImpl(
        title = "Cash from Investment Activities",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }

    override val financing = DynamicReportRowImpl(
        title = "Cash from Financing Activities",
        removable = false,
        appendable = true
    ).apply {
        expand()
    }
}