@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.ColumnsManagerImpl
import symphony.internal.DataCollectionImpl

inline fun <T> scrollableListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: ActionsManager<T>
): ScrollableList<T> = DataCollectionImpl(
    paginator, selector, actionsManager,
    ColumnsManagerImpl(mutableSetOf())
)

inline fun <T> scrollableListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): ScrollableList<T> = DataCollectionImpl(
    paginator, selector, actionsOf(selector) {},
    ColumnsManagerImpl(mutableSetOf())
)