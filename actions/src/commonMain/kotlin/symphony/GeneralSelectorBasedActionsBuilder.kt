@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.Action0
import kevlar.builders.Actions0Builder

class GeneralSelectorBasedActionsBuilder<T> @PublishedApi internal constructor(
    primary: MutableList<Action0<Unit>> = mutableListOf(),
    single: MutableList<Action0<Unit>> = mutableListOf(),
    multi: MutableList<Action0<Unit>> = mutableListOf(),
    global: MutableList<Action0<Unit>> = mutableListOf(),
    filters: MutableSet<String> = mutableSetOf()
) : AbstractGeneralSelectorBasedActionsBuilder<T>(primary, single, multi, global, filters) {


    @PublishedApi
    internal val globalActionsContainer = mutableListOf<Actions0Builder<Unit>.(SelectedGlobal<T>) -> Unit>()

    inline fun global(noinline builder: Actions0Builder<Unit>.(SelectedGlobal<T>) -> Unit) {
        globalActionsContainer.add(builder)
    }

    fun buildGlobalSelectActions(state: SelectedGlobal<T>) = globalActions.apply {
        globalActionsContainer.forEach { builder -> builder(state) }
    }.actions.applyFilters()

    override fun buildActions(selected: Selected<T>) = buildList<Action0<Unit>> {
        addAll(buildPrimaryActions())
        when (selected) {
            is SelectedNone -> {}
            is SelectedItem -> addAll(buildSingleSelectActions(selected.row.item))
            is SelectedItems -> addAll(buildMultiSelectActions(selected.page.entries.flatMap { (_, v) -> v }.map { it.item }.toList()))
            is SelectedGlobal -> addAll(buildGlobalSelectActions(selected))
        }
    }
}