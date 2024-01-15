@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.GroupedTableImpl
import symphony.internal.LinearTableImpl
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
inline fun <T> tableOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsManager<T> = columnsOf()
): Table<T> = LinearTableImpl(paginator, selector, actions, columns)

@JvmSynthetic
inline fun <T> tableOf(
    paginator: GroupedPaginationManager<*, T>,
    selector: GroupedSelectionManager<*, T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsManager<T> = columnsOf()
): Table<T> = GroupedTableImpl(paginator, selector, actions, columns)

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
fun <T> tableOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsBuilder<T>.() -> Unit
): Table<T> = LinearTableImpl(paginator, selector, actions, columnsOf(columns))

fun <T> tableOf(
    paginator: GroupedPaginationManager<*, T>,
    selector: GroupedSelectionManager<*, T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsBuilder<T>.() -> Unit
): Table<T> = GroupedTableImpl(paginator, selector, actions, columnsOf(columns))

//@JvmSynthetic
//fun <T> tableOf(
//    items: Collection<T>,
//    capacity: Int = items.size,
//    columns: ColumnsBuilder<T>.() -> Unit
//): Table<T> = tableOf(linearPaginatorOf(items, capacity), columns = columns)

inline fun <T> tableOf(
    paginator: PaginationManager<T, *, *>,
    selector: SelectionManager<T, *> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsManager<T> = columnsOf()
): Table<T> = when {
    paginator is LinearPaginationManager && selector is LinearSelectionManager -> tableOf(paginator, selector, actions, columns)
    paginator is GroupedPaginationManager<*, T> && selector is GroupedSelectionManager<*, T> -> tableOf(paginator, selector, actions, columns)
    else -> throw IllegalArgumentException("Unsupported combination of ${paginator::class.simpleName} and ${selector::class.simpleName}")
}