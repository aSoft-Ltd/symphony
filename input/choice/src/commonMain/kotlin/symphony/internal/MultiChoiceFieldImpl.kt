package symphony.internal

import cinematic.mutableLiveOf
import neat.Validator
import neat.Validators
import neat.required
import symphony.Changer
import symphony.ErrorFeedback
import symphony.Feedbacks
import symphony.Label
import symphony.MultiChoiceField
import symphony.MultiSelectedChoice
import symphony.Option
import symphony.SearchBy
import symphony.Visibility
import symphony.toWarnings
import symphony.internal.MultiChoiceFieldStateImpl as State

@PublishedApi
internal class MultiChoiceFieldImpl<T : Any>(
    private val backer: FieldBacker<MutableList<T>>,
    label: String,
    hint: String,
    value: List<T>?,
    items: Collection<T>,
    override val mapper: (T) -> Option,
    private val filter: (item: T, key: String) -> Boolean,
    searchBy: SearchBy,
    visibility: Visibility,
    private val onChange: Changer<List<T>>?,
    factory: (Validators<List<T>>.() -> Validator<List<T>>)?
) : AbstractMultiChoiceField<T>(label, factory), MultiChoiceField<T> {

    override val items get() = state.value.items

    override val optionLabels get() = options.map { it.label }

    override val optionValues get() = options.map { it.value }

    override val selected get() = state.value.selected

    override val options: List<Option>
        get() = run {
            val selected = selected.values
            items.map {
                val o = mapper(it)
                if (selected.contains(o.value)) o.copy(selected = true) else o
            }
        }

    override val optionsWithSelectLabel get() = (listOf(Option("Select $name", "")) + options)

    private fun Collection<T>.findItemWithLabel(l: String) = find { mapper(it).label == l }

    private fun Collection<T>.findItemWithOption(o: Option): T? = find { mapper(it) == o }

    private fun Collection<T>.findItemWithValue(v: String): T? = find { mapper(it).value == v }

    override fun addSelectedItem(item: T) {
        set(output + item)
    }

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

    override fun unselectItem(i: T) {
        val output = state.value.output
        if (!output.contains(i)) return
        set(output - i)
    }

    override fun unselectValue(v: String) {
        val item = output.findItemWithValue(v) ?: return
        unselectItem(item)
    }

    override fun unselectLabel(l: String) {
        val item = output.findItemWithLabel(l) ?: return
        unselectItem(item)
    }

    override fun unselectAll() = set(emptyList())

    override fun set(value: List<T>?) {
        val res = validator.validate(value)
        val output = res.value.toMutableList()
        backer.asProp?.set(output)
        state.value = state.value.copy(
            selected = output.toSelectedChoice(),
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(output)
    }

    override fun toggleSelectedValue(v: String) {
        if (selected.values.contains(v)) {
            unselectValue(v)
        } else {
            addSelectedValue(v)
        }
    }

    override val initial = State<T>(
        name = backer.name,
        label = Label(label, this.validator.required),
        options = items,
        items = items,
        searchBy = searchBy,
        key = "",
        selected = (value ?: backer.asProp?.get())?.toSelectedChoice() ?: MultiSelectedChoice(emptyList(), emptyList(), emptySet()),
        hint = hint,
        required = this.validator.required,
        visibility = visibility,
        feedbacks = Feedbacks(emptyList()),
    )

    override val state = mutableLiveOf(initial)

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

    override fun replaceItems(items: Collection<T>) {
        state.value = state.value.copy(items = items, options = items)
    }

    override val name = backer.name

    override fun setSearchByFiltering() = setSearchBy(SearchBy.Filtering)

    override fun setSearchByOrdering() = setSearchBy(SearchBy.Ordering)

    override fun setSearchBy(sb: SearchBy) {
        val s = state.value.searchBy
        if (s == sb) return
        state.value = state.value.copy(searchBy = sb)
    }

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

    override fun errors(errors: List<String>) {
        if (errors.isEmpty()) return
        val feedbacks = state.value.feedbacks.items + errors.map { ErrorFeedback(it) }
        state.value = state.value.copy(feedbacks = Feedbacks(feedbacks))
    }

    private fun List<T>.toSelectedChoice(): MultiSelectedChoice<T> {
        val options = map { mapper(it) }
        return MultiSelectedChoice(
            items = this,
            options = options,
            values = options.map { it.value }.toSet()
        )
    }
}