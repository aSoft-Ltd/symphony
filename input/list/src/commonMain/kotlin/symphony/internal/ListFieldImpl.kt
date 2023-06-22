package symphony.internal

import kollections.List
import kollections.iEmptyList
import kollections.toIList
import neat.Validator
import neat.Validators
import symphony.ListField
import kotlin.reflect.KMutableProperty0

internal class ListFieldImpl<E>(
    name: KMutableProperty0<List<E>>,
    label: String,
    value: List<E>,
    hidden: Boolean,
    hint: String,
    validator: (Validators<List<E>>.() -> Validator<List<E>>)?
) : AbstractBaseField<List<E>>(name, label, value, hidden, hint, validator), ListField<E> {

    override val output: List<E> get() = state.value.output ?: iEmptyList()

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
        set(list.toIList())
    }
}