@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.Action0
import kevlar.builders.Actions0Builder
import kollections.plus
import kollections.mutableMapOf
import kollections.associateBy
import kollections.filterKeys
import kollections.toList
import kollections.List
import kollections.size
import kollections.values

class FixedActionsBuilder @PublishedApi internal constructor(
    private val builder: Actions0Builder<Unit>.() -> Unit,
    @PublishedApi
    internal val filters: MutableSet<String> = mutableSetOf()
) {
    @PublishedApi
    internal val extraActions = mutableMapOf<String, Action0<Unit>>()

    internal inline fun List<Action0<Unit>>.applyFilters() = associateBy {
        it.key
    }.filterKeys {
        !filters.contains(it.lowercase())
    }.values.toList()

    fun buildActions() : List<Action0<Unit>> {
        val actions = Actions0Builder<Unit>().apply(builder).actions.toList()
        val extras = extraActions.values
        return (actions + extras).applyFilters()
    }
}