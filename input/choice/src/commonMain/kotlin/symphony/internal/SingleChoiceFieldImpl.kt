package symphony.internal

import kollections.Collection
import kollections.List
import kollections.toIList
import neat.Validator
import neat.Validators
import symphony.Option
import symphony.SingleChoiceField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class SingleChoiceFieldImpl<T>(
    name: KMutableProperty0<T>,
    label: String,
    value: T,
    override val items: Collection<T & Any>,
    override val mapper: (T & Any) -> Option,
    hidden: Boolean,
    hint: String,
    validator: (Validators<T>.() -> Validator<T>)?
) : AbstractBaseField<T>(name, label, value, hidden, hint, validator), SingleChoiceField<T> {

    override val selectedItem: T? get() = state.value.output

    override val selectedOption: Option? get() = selectedItem?.let(mapper)?.copy(selected = true)

    override fun options(withSelect: Boolean): List<Option> = (if (withSelect) {
        listOf(Option("Select ${state.value.label.capitalizedWithoutAstrix()}", ""))
    } else {
        emptyList()
    } + items.toList().map {
        val o = mapper(it)
        if (it == state.value.output) o.copy(selected = true) else o
    }).toIList()

    override fun selectOption(option: Option) {
        val item = items.find { mapper(it).value == option.value }
        if (item != null) set(item)
    }

    override fun select(item: T) = set(item)

    override fun selectValue(optionValue: String) {
        val item = items.find { mapper(it).value == optionValue }
        if (item != null) set(item)
    }

    override fun selectLabel(optionLabel: String) {
        val item = items.find { mapper(it).label == optionLabel }
        if (item != null) set(item)
    }

    override fun selectItem(item: T) = set(item)

    override fun unselect() {
        state.value = state.value.copy(output = null)
    }
}