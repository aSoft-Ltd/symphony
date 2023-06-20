package symphony.internal

import kollections.Collection
import kollections.List
import kollections.toIList
import neat.Validator
import neat.Validators
import symphony.Option
import symphony.TransformingSingleChoiceField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class TransformingSingleChoiceFieldImpl<I, O>(
    name: KMutableProperty0<O>,
    label: String,
    value: O,
    override val transformer: (I) -> O,
    override val items: Collection<I>,
    override val mapper: (I) -> Option,
    hidden: Boolean,
    hint: String,
    validator: (Validators<O>.() -> Validator<O>)?
) : AbstractTransformingField<I, O>(name, transformer, label, value, hidden, hint, validator), TransformingSingleChoiceField<I, O> {

    var selectedInput: I? = null

    override val selectedItem: O? get() = selectedInput?.let(transformer)

    override val selectedOption: Option? get() = selectedInput?.let(mapper)?.copy(selected = true)

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

    override fun select(item: I) = set(item)

    override fun selectValue(optionValue: String) {
        val item = items.find { mapper(it).value == optionValue }
        if (item != null) set(item)
    }

    override fun selectLabel(optionLabel: String) {
        val item = items.find { mapper(it).label == optionLabel }
        if (item != null) set(item)
    }

    override fun selectItem(item: I) = set(item)

    override fun unselect() {
        state.value = state.value.copy(output = null)
    }
}