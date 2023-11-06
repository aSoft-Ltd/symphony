@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.TableImpl
import kotlin.jvm.JvmSynthetic
import symphony.internal.GroupedTableImpl
import symphony.internal.LinearTableImpl

@Deprecated("in favour of LinearPaginationManager")
@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: SelectorBasedActionsManager<T>,
    columns: ColumnsManager<T>
): Table<T> = TableImpl(paginator, selector, actions, columns)

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@Deprecated("in favour of LinearPaginationManager")
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: SelectorBasedActionsManager<T>,
    columns: ColumnsBuilder<T>.() -> Unit
): Table<T> = TableImpl(paginator, selector, actions, columnsOf(columns))

@Deprecated("in favour of LinearPaginationManager")
@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: SelectorBasedActionsManager<T>
): Table<T> = TableImpl(paginator, selector, actions, columnsOf())

@Deprecated("in favour of LinearPaginationManager")
@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): Table<T> = TableImpl(paginator, selector, actionsOf(selector) {}, columnsOf())

@Deprecated("in favour of LinearPaginationManager")
@JvmSynthetic
inline fun <T> tableOf(paginator: PaginationManager<T>): Table<T> {
    val selector = SelectionManager(paginator)
    return TableImpl(paginator, selector, actionsOf(selector) {}, columnsOf())
}

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@Deprecated("in favour of LinearPaginationManager")
@JvmSynthetic
fun <T> tableOf(
    items: Collection<T>,
    capacity: Int = items.size,
    columns: ColumnsBuilder<T>.() -> Unit
): Table<T> {
    val paginator = CollectionPaginator(items, capacity)
    val selector = SelectionManager(paginator)
    return TableImpl(paginator, selector, actionsOf(selector) {}, columnsOf(columns))
}

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@Deprecated("in favour of LinearPaginationManager")
@JvmSynthetic
inline fun <T> simpleTableOf(
    items: Collection<T>,
    noinline columns: ColumnsBuilder<T>.() -> Unit
): Table<T> {
    val paginator = SinglePagePaginator(items)
    paginator.loadFirstPage()
    val selector = SelectionManager(paginator)
    return TableImpl(paginator, selector, actionsOf(), columnsOf(columns))
}

@JvmSynthetic
inline fun <T> tableOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsManager<T> = columnsOf()
): ITable<T> = LinearTableImpl(paginator, selector, actions, columns)

@JvmSynthetic
inline fun <T> tableOf(
    paginator: GroupedPaginationManager<*, T>,
    selector: GroupedSelectionManager<*, T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsManager<T> = columnsOf()
): ITable<T> = GroupedTableImpl(paginator, selector, actions, columns)

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
): ITable<T> = LinearTableImpl(paginator, selector, actions, columnsOf(columns))

fun <T> tableOf(
    paginator: GroupedPaginationManager<*, T>,
    selector: GroupedSelectionManager<*, T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions(),
    columns: ColumnsBuilder<T>.() -> Unit
): ITable<T> = GroupedTableImpl(paginator, selector, actions, columnsOf(columns))

@JvmSynthetic
inline fun <T> tableOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions()
): ITable<T> = LinearTableImpl(paginator, selector, actions, columnsOf())

@JvmSynthetic
inline fun <T> tableOf(
    paginator: GroupedPaginationManager<*, T>,
    selector: GroupedSelectionManager<*, T> = selectorOf(paginator),
    actions: SelectorBasedActionsManager<T> = emptyActions()
): ITable<T> = GroupedTableImpl(paginator, selector, actions, columnsOf())

@JvmSynthetic
fun <T> linearTableOf(
    items: Collection<T>,
    capacity: Int = items.size,
    columns: ColumnsBuilder<T>.() -> Unit
): ITable<T> = tableOf(linearPaginatorOf(items, capacity), columns = columns)