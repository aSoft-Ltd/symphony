@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.Action0
import kevlar.builders.Actions0Builder
import kollections.toIList

class GroupedSelectorBasedActionsBuilder<G, T> @PublishedApi internal constructor(
    primary: MutableList<Action0<Unit>> = mutableListOf(),
    single: MutableList<Action0<Unit>> = mutableListOf(),
    multi: MutableList<Action0<Unit>> = mutableListOf(),
    global: MutableList<Action0<Unit>> = mutableListOf(),
    filters: MutableSet<String> = mutableSetOf()
) : AbstractSelectorBasedActionsBuilder<T, GroupedSelected<G, T>>(primary, single, multi, global, filters) {


    @PublishedApi
    internal val globalActionsContainer = mutableListOf<Actions0Builder<Unit>.(GroupedSelectedGlobal<G, T>) -> Unit>()

    inline fun global(noinline builder: Actions0Builder<Unit>.(GroupedSelectedGlobal<G, T>) -> Unit) {
        globalActionsContainer.add(builder)
    }

    fun buildGlobalSelectActions(state: GroupedSelectedGlobal<G, T>) = globalActions.apply {
        globalActionsContainer.forEach { builder -> builder(state) }
    }.actions.applyFilters()

    override fun buildActions(selected: GroupedSelected<G, T>) = buildList {
        addAll(buildPrimaryActions())
        when (selected) {
            is GroupedSelectedNone -> {}
            is GroupedSelectedItem -> addAll(buildSingleSelectActions(selected.row.item))
            is GroupedSelectedItems -> addAll(buildMultiSelectActions(selected.page.toIList().flatMap { (_, v) -> v }.map { it.item }.toIList()))
            is GroupedSelectedGlobal -> addAll(buildGlobalSelectActions(selected))
        }
    }.toIList()
}