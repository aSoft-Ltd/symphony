package symphony.internal

import cinematic.LiveList
import cinematic.mutableLiveListOf
import kollections.List
import kollections.toTypedArray
import symphony.BaseField
import symphony.DoubleField
import symphony.NumberField
import symphony.SectionRow
import symphony.StaticSectionRowForm
import symphony.TextField

internal class StaticSectionRowFormImpl(
    override val label: BaseField<String> = TextField(name = "Label"),
    override val total: NumberField<Double> = DoubleField(name = "Total"),
    override val children: LiveList<SectionRow>
) : StaticSectionRowForm {
    constructor(
        label: String,
        total: Double,
        children: List<SectionRow>
    ) : this(label = TextField(name = "Label", value = label), total = DoubleField(name = "Total", value = total), children = mutableLiveListOf(*children.toTypedArray()))
}