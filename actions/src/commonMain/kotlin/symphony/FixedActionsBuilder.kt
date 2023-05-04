@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.Action0
import kevlar.builders.Actions0Builder
import kollections.List
import kollections.toIList

class FixedActionsBuilder @PublishedApi internal constructor(
    primary: MutableList<Action0<Unit>> = mutableListOf(),
    @PublishedApi
    internal val filters: MutableSet<String> = mutableSetOf()
) {
    @PublishedApi
    internal val primaryActions = Actions0Builder(primary)

    inline fun primary(builder: Actions0Builder<Unit>.() -> Unit) {
        primaryActions.apply(builder)
    }

    private inline fun Collection<Action0<Unit>>.applyFilters() = associateBy {
        it.key
    }.filterKeys {
        !filters.contains(it.lowercase())
    }.values.toIList()

    fun buildPrimaryActions() = primaryActions.actions.applyFilters()

    fun buildActions() = buildPrimaryActions()
}