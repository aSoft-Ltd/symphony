package symphony.internal

import neat.Validator
import neat.Validators
import symphony.Changer
import symphony.Option
import symphony.TransformingSingleChoiceField
import symphony.Visibility
import symphony.internal.transforming.BaseTransformingFieldImpl
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class TransformingSingleChoiceFieldImpl<I, O>(
    property: KMutableProperty0<O?>,
    label: String,
    private val transformer: (I?) -> O?,
    override val items: Collection<I & Any>,
    override val mapper: (I) -> Option,
    visibility: Visibility,
    hint: String,
    onChange: Changer<O>?,
    factory: (Validators<O>.() -> Validator<O>)?
) : BaseTransformingFieldImpl<I, O>(property, label, visibility, hint, transformer, onChange, factory), TransformingSingleChoiceField<I, O> {

    override val selectedItem: O? get() = selectedInput?.let(transformer)

    override val selectedOption: Option? get() = selectedInput?.let(mapper)?.copy(selected = true)

    override fun options(withSelect: Boolean): List<Option> = (if (withSelect) {
        listOf(Option("Select option", ""))
    } else {
        emptyList()
    } + items.map {
        val o = mapper(it)
        if (it == state.value.output) o.copy(selected = true) else o
    })

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

    val selectedInput get() = state.value.input

    override fun replaceItems(items: Collection<I & Any>) {
        TODO("Not yet implemented")
    }
}