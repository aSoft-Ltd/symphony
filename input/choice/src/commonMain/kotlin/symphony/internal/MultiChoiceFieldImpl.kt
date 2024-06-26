package symphony.internal

import kollections.Collection
import kollections.List
import kollections.listOf
import kollections.plus
import kollections.MutableList
import kollections.contains
import kollections.find
import kollections.map
import kollections.toSet
import neat.Validator
import neat.Validators
import symphony.Changer
import symphony.MultiChoiceField
import symphony.Option
import symphony.Visibility

@PublishedApi
internal class MultiChoiceFieldImpl<T : Any>(
    backer: FieldBacker<MutableList<T>>,
    label: String,
    value: List<T>,
    override val items: Collection<T>,
    override val mapper: (T) -> Option,
    visibility: Visibility,
    onChange: Changer<List<T>>?,
    factory: (Validators<List<T>>.() -> Validator<List<T>>)?
) : ListFieldImpl<T>(backer, value, label, visibility, onChange, factory), MultiChoiceField<T> {

    override val optionLabels get() = options.map { it.label }

    override val optionValues get() = options.map { it.value }

    override val selectedValues get() = output.map { mapper(it).value }.toSet()

    override val selectedItems get() = output

    override val selectedOptions get() = output.map(mapper)

    override val options: List<Option>
        get() = run {
            val selected = selectedValues
            items.map {
                val o = mapper(it)
                if (selected.contains(o.value)) o.copy(selected = true) else o
            }
        }

    override val optionsWithSelectLabel get() = (listOf(Option("Select $name", "")) + options)

    private fun Collection<T>.findItemWithLabel(l: String) = find { mapper(it).label == l }

    private fun Collection<T>.findItemWithOption(o: Option): T? = find { mapper(it) == o }

    private fun Collection<T>.findItemWithValue(v: String): T? = find { mapper(it).value == v }

    override fun addSelectedItem(item: T) = add(item)

    override fun addSelectedOption(o: Option) {
        val item = items.findItemWithOption(o) ?: return
        addSelectedItem(item)
    }

    override fun addSelectedValue(v: String) {
        val item = items.findItemWithValue(v) ?: return
        addSelectedItem(item)
    }

    override fun addSelectLabel(l: String) {
        val item = items.findItemWithLabel(l) ?: return
        addSelectedItem(item)
    }

    override fun isSelected(item: T): Boolean = output.contains(item)

    override fun isSelectedLabel(l: String) = output.findItemWithLabel(l) != null

    override fun isSelectedOption(o: Option) = output.findItemWithOption(o) != null

    override fun isSelectedValue(v: String) = output.findItemWithValue(v) != null

    override fun unselectOption(o: Option) {
        val item = output.findItemWithOption(o) ?: return
        unselectItem(item)
    }

    override fun unselectItem(i: T) = remove(i)

    override fun unselectValue(v: String) {
        val item = output.findItemWithValue(v) ?: return
        unselectItem(item)
    }

    override fun unselectLabel(l: String) {
        val item = output.findItemWithLabel(l) ?: return
        unselectItem(item)
    }

    override fun unselectAll() = removeAll(output)

    override fun toggleSelectedValue(v: String) {
        if (selectedValues.contains(v)) {
            unselectValue(v)
        } else {
            addSelectedValue(v)
        }
    }

    override fun toggleSelectedOption(o: Option) = toggleSelectedValue(o.value)

    override fun toggleSelectedItem(i: T) = if (isSelected(i)) {
        unselectItem(i)
    } else {
        addSelectedItem(i)
    }

    override fun toggleSelectedLabel(l: String) {
        if (isSelectedLabel(l)) {
            unselectLabel(l)
        } else {
            addSelectLabel(l)
        }
    }

    val name = backer.name
}