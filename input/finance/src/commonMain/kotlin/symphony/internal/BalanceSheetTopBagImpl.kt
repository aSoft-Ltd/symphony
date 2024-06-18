package symphony.internal

import cinematic.mutableLiveOf
import symphony.BalanceSheetTopBag
import symphony.StaticSectionRowForm

internal class BalanceSheetTopBagImpl(
    override val label: String,
    override val current: StaticSectionRowForm,
    override val fixed: StaticSectionRowForm
) : BalanceSheetTopBag {
    override val total = mutableLiveOf(current.amount + fixed.amount)

    private val StaticSectionRowForm.amount get() = total.output ?: 0.0
}