@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.Action0
import kevlar.builders.Actions0Builder
import kollections.toIList

class FixedActionsBuilder @PublishedApi internal constructor(
    private val builder: Actions0Builder<Unit>.() -> Unit,
    @PublishedApi
    internal val filters: MutableSet<String> = mutableSetOf()
) {
    @PublishedApi
    internal val extraActions = mutableMapOf<String, Action0<Unit>>()

    private inline fun Collection<Action0<Unit>>.applyFilters() = associateBy {
        it.key
    }.filterKeys {
        !filters.contains(it.lowercase())
    }.values.toIList()

    fun buildActions() = (Actions0Builder<Unit>().apply(builder).actions + extraActions.values).applyFilters()
}