package symphony.internal

import kollections.Collection
import kollections.List
import kollections.toIList
import symphony.Label
import symphony.ListInputField
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.RequirementValidator

@PublishedApi
internal class ListInputFieldImpl<E>(
    override val name: String,
    override val label: Label,
    override val hint: String,
    private val value: Collection<E>?,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    override val maxItems: Int?,
    override val minItems: Int?,
    val validator: ((List<E>) -> Unit)?
) : PlainDataListField<E>(value), ListInputField<E> {

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired)
    )

    override fun add(item: E) = set((output + item).toIList())

    override fun addAll(items: List<E>) = set((output + items).toIList())

    override fun remove(item: E) = set((output - item).toIList())

    override fun removeAll(items: List<E>) = set((output - items).toIList())

    override fun update(item: E, updater: () -> E) {
        val list = output.toMutableList()
        val idx = list.indexOf(item)
        if (idx == -1) return
        list.remove(item)
        list.add(idx, updater())
        setter.set(list.toIList())
    }
}