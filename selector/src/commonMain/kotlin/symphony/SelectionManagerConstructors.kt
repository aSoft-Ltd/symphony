@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.SelectionManagerImpl

inline fun <T> SelectionManager(
    paginator: PaginationManager<T>
): SelectionManager<T> = SelectionManagerImpl(paginator)