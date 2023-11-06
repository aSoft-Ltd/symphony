@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.BluntLinearSelectionManager
import symphony.internal.GroupedSelectionManagerImpl
import symphony.internal.LinearSelectionManagerImpl
import symphony.internal.SelectionManagerImpl

@Deprecated("In favour of LinearSelectionManager")
inline fun <T> SelectionManager(
    paginator: PaginationManager<T>
): SelectionManager<T> = SelectionManagerImpl(paginator)

inline fun selectorOf(): LinearSelectionManager<Nothing> = BluntLinearSelectionManager.instance

inline fun <T> selectorOf(
    paginator: LinearPaginationManager<T>
): LinearSelectionManager<T> = LinearSelectionManagerImpl(paginator)

inline fun <G, T> selectorOf(
    paginator: GroupedPaginationManager<G, T>
): GroupedSelectionManager<G, T> = GroupedSelectionManagerImpl(paginator)