package symphony.internal

import kollections.List
import kollections.iEmptyList
import kollections.toIList
import neat.ValidationFactory
import neat.required
import symphony.Changer
import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.SingleChoiceField
import symphony.Visibility
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class SingleChoiceFieldImpl<T>(
    property: KMutableProperty0<T?>,
    label: String,
    override val items: List<T & Any>,
    override val mapper: (T & Any) -> Option,
    private val filter: (item: T & Any, key: String) -> Boolean,
    visibility: Visibility,
    hint: String,
    onChange: Changer<T>? = null,
    factory: ValidationFactory<T>?
) : AbstractSingleChoiceField<T>(property, label, visibility, hint, onChange, factory), SingleChoiceField<T> {

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

    override fun searchByFiltering(key: String?) {
        state.value = state.value.copy(
            items = if (key.isNullOrEmpty()) items else items.filter { filter(it,key) }
        )
    }

    override fun searchByOrdering(key: String?) {
        state.value = state.value.copy(
            items = if (key.isNullOrEmpty()) {
                items
            } else {
                val partitions = items.partition { filter(it,key) }
                (partitions.first + partitions.second).toIList()
            }
        )
    }

    override fun clearSearch() {
        state.value = state.value.copy(items = items)
    }

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

    override val initial = State(
        name = property.name,
        label = Label(label, this.validator.required),
        items = items,
        selectedItem = property.get(),
        selectedOption = property.get()?.let { mapper(it) },
        hint = hint,
        required = this.validator.required,
        output = property.get(),
        visibility = visibility,
        feedbacks = Feedbacks(iEmptyList()),
    )
}