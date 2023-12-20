@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.Action0
import kevlar.builders.Actions0Builder
import kollections.List
import kollections.mutableListOf
import kollections.mutableSetOf
import kollections.Collection
import kollections.MutableList
import kollections.MutableSet
import kollections.add
import kollections.associateBy
import kollections.contains
import kollections.filterKeys
import kollections.forEach
import kollections.toList
import kollections.values
import symphony.selected.Selected

abstract class AbstractSelectorBasedActionsBuilder<T, S : Selected<T>> @PublishedApi internal constructor(
    primary: MutableList<Action0<Unit>> = mutableListOf(),
    single: MutableList<Action0<Unit>> = mutableListOf(),
    multi: MutableList<Action0<Unit>> = mutableListOf(),
    global: MutableList<Action0<Unit>> = mutableListOf(),
    @PublishedApi
    internal val filters: MutableSet<String> = mutableSetOf()
) {
    @PublishedApi
    internal val primaryActions = Actions0Builder(primary)

    @PublishedApi
    internal val singleActions = Actions0Builder(single)

    @PublishedApi
    internal val singleActionsContainer = mutableListOf<Actions0Builder<Unit>.(T) -> Unit>()

    @PublishedApi
    internal val multiActionsBuilder = Actions0Builder(multi)

    @PublishedApi
    internal val multiActionsContainer = mutableListOf<Actions0Builder<Unit>.(List<T>) -> Unit>()

    @PublishedApi
    internal val globalActions = Actions0Builder(global)

    inline fun primary(builder: Actions0Builder<Unit>.() -> Unit) {
        primaryActions.apply(builder)
    }

    inline fun single(noinline builder: Actions0Builder<Unit>.(T) -> Unit) {
        singleActionsContainer.add(builder)
    }

    inline fun multi(noinline builder: Actions0Builder<Unit>.(List<T>) -> Unit) {
        multiActionsContainer.add(builder)
    }

    protected inline fun Collection<Action0<Unit>>.applyFilters() = associateBy {
        it.key
    }.filterKeys {
        !filters.contains(it.lowercase())
    }.values.toList()

    fun buildPrimaryActions() = primaryActions.actions.applyFilters()

    fun buildSingleSelectActions(selected: T) = singleActions.apply {
        singleActionsContainer.forEach { builder -> builder(selected) }
    }.actions.applyFilters()

    fun buildMultiSelectActions(selected: List<T>) = multiActionsBuilder.apply {
        multiActionsContainer.forEach { builder -> builder(selected) }
    }.actions.applyFilters()

    abstract fun buildActions(selected: S): List<Action0<Unit>>
}