package symphony.internal

import kollections.listOf
import symphony.BalanceSheetForm

@PublishedApi
internal class BalanceSheetFormImpl : BalanceSheetForm {
    override val assets = BalanceSheetTopBagImpl(
        label = "Assets",
        current = StaticSectionRowFormImpl(
            label = "Current Assets",
            total = 0.0,
            children = listOf()
        ),
        fixed = StaticSectionRowFormImpl(
            label = "Non Current Assets",
            total = 0.0,
            children = listOf()
        )
    )

    override val equity = StaticSectionRowFormImpl(
        label = "Equity",
        total = 0.0,
        children = listOf()
    )

    override val liabilities = BalanceSheetTopBagImpl(
        label = "Liabilities",
        current = StaticSectionRowFormImpl(
            label = "Current Liabilities",
            total = 0.0,
            children = listOf()
        ),
        fixed = StaticSectionRowFormImpl(
            label = "Long Term Liabilities",
            total = 0.0,
            children = listOf()
        )
    )
}