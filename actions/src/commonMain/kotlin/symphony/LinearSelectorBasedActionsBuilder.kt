@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.Action0
import kevlar.builders.Actions0Builder
import kollections.MutableSet
import kollections.MutableList
import kollections.add
import kollections.addAll
import kollections.buildList
import kollections.entries
import kollections.flatMap
import kollections.component1
import kollections.component2
import kollections.forEach
import kollections.map
import kollections.mutableListOf
import kollections.mutableSetOf
import kollections.toList
import symphony.selected.LinearSelected
import symphony.selected.LinearSelectedGlobal
import symphony.selected.LinearSelectedItem
import symphony.selected.LinearSelectedItems
import symphony.selected.LinearSelectedNone

class LinearSelectorBasedActionsBuilder<T> @PublishedApi internal constructor(
    primary: MutableList<Action0<Unit>> = mutableListOf(),
    single: MutableList<Action0<Unit>> = mutableListOf(),
    multi: MutableList<Action0<Unit>> = mutableListOf(),
    global: MutableList<Action0<Unit>> = mutableListOf(),
    filters: MutableSet<String> = mutableSetOf()
) : AbstractSelectorBasedActionsBuilder<T, LinearSelected<T>>(primary, single, multi, global, filters){


    @PublishedApi
    internal val globalActionsContainer = mutableListOf<Actions0Builder<Unit>.(LinearSelectedGlobal<T>) -> Unit>()

    inline fun global(noinline builder: Actions0Builder<Unit>.(LinearSelectedGlobal<T>) -> Unit) {
        globalActionsContainer.add(builder)
    }
    fun buildGlobalSelectActions(state: LinearSelectedGlobal<T>) = globalActions.apply {
        globalActionsContainer.forEach { builder -> builder(state) }
    }.actions.applyFilters()

    override fun buildActions(selected: LinearSelected<T>) = buildList<Action0<Unit>> {
        addAll(buildPrimaryActions())
        when (selected) {
            is LinearSelectedNone -> {}
            is LinearSelectedItem -> addAll(buildSingleSelectActions(selected.row.item))
            is LinearSelectedItems -> addAll(buildMultiSelectActions(selected.page.entries.flatMap { (_, v) -> v }.map { it.item }.toList()))
            is LinearSelectedGlobal -> addAll(buildGlobalSelectActions(selected))
        }
    }
}