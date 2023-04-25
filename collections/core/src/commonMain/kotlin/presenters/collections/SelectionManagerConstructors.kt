@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import presenters.collections.internal.SelectionManagerImpl

@Deprecated("use symphony instead")
inline fun <T> SelectionManager(
    paginator: PaginationManager<T>
): SelectionManager<T> = SelectionManagerImpl(paginator)