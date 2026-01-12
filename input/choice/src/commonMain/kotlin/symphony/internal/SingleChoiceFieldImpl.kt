package symphony.internal

import neat.ValidationFactory
import neat.required
import symphony.Changer
import symphony.ErrorFeedback
import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.SearchBy
import symphony.SingleChoiceField
import symphony.SingleSelectedChoice
import symphony.Visibility
import symphony.toWarnings
import symphony.internal.SingleChoiceFieldStateImpl as State

@PublishedApi
internal class SingleChoiceFieldImpl<T>(
    private val backer: FieldBacker<T>,
    label: String,
    value: T?,
    items: Collection<T & Any>,
    override val mapper: (T & Any) -> Option,
    private val filter: (item: T & Any, key: String) -> Boolean,
    private val searchBy: SearchBy,
    visibility: Visibility,
    hint: String,
    private val onChange: Changer<T>? = null,
    factory: ValidationFactory<T>?
) : AbstractSingleChoiceField<T>(label, factory), SingleChoiceField<T> {

    override val items: Collection<T & Any> get() = state.value.items

    override val selected get() = state.value.selected

    override fun set(value: T?) {
        val res = validator.validate(value)
        val output = res.value
        backer.asProp?.set(output)
        state.value = state.value.copy(
            selected = output?.toSelectedChoice(),
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(output)
    }

    override fun options(withSelect: Boolean): List<Option> = (if (withSelect) {
        listOf(Option("Select ${state.value.label.capitalizedWithoutAstrix()}", ""))
    } else {
        emptyList()
    } + items.map {
        val o = mapper(it)
        if (it == state.value.output) o.copy(selected = true) else o
    })

    override fun errors(errors: List<String>) {
        if (errors.isEmpty()) return
        val feedbacks = state.value.feedbacks.items + errors.map { ErrorFeedback(it) }
        state.value = state.value.copy(feedbacks = Feedbacks(feedbacks))
    }

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

    override fun setSearchBy(sb: SearchBy) {
        val s = state.value.searchBy
        if (s == sb) return
        state.value = state.value.copy(searchBy = sb)
    }

    override fun setSearchByFiltering() = setSearchBy(SearchBy.Filtering)

    override fun setSearchByOrdering() = setSearchBy(SearchBy.Ordering)

    override fun search() {
        val key = state.value.key
        val found = if (key.isEmpty()) items else when (state.value.searchBy) {
            SearchBy.Filtering -> items.filter { filter(it, key) }
            SearchBy.Ordering -> {
                val partitions = items.partition { filter(it, key) }
                (partitions.first + partitions.second)
            }
        }
        state.value = state.value.copy(items = found)
    }

    override fun appendSearchKey(key: String?) = setSearchKey(state.value.key + (key ?: ""))

    override fun clearSearchKey(): String {
        val key = ""
        state.value = state.value.copy(
            key = key,
            items = state.value.options
        )
        return key
    }

    override fun backspaceSearchKey(): String {
        val key = state.value.key
        if (key.isEmpty()) return ""
        return setSearchKey(key.dropLast(1))
    }

    override fun setSearchKey(key: String?): String {
        val k = key ?: ""
        state.value = state.value.copy(key = k)
        return k
    }

    override fun unselect() {
        state.value = state.value.copy(selected = null)
        clearSearchKey()
    }

    override val initial = State(
        name = backer.name,
        label = Label(label, this.validator.required),
        options = items,
        items = items,
        searchBy = searchBy,
        key = "",
        selected = (value ?: backer.asProp?.get())?.toSelectedChoice(),
        hint = hint,
        required = this.validator.required,
        visibility = visibility,
        feedbacks = Feedbacks(emptyList()),
    )

    override fun replaceItems(items: Collection<T & Any>) {
        state.value = state.value.copy(items = items, options = items)
    }

    private fun T.toSelectedChoice(): SingleSelectedChoice<T>? {
        val item = this ?: return null
        return SingleSelectedChoice(item, mapper(item))
    }
}