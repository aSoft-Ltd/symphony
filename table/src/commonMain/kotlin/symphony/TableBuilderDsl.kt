@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.TableImpl
import kotlin.jvm.JvmSynthetic

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
fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: SelectorBasedActionsManager<T>,
    columns: ColumnsBuilder<T>.() -> Unit
): Table<T> = TableImpl(paginator, selector, actions, columnsOf(emptyList(), columns))

@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actions: SelectorBasedActionsManager<T>
): Table<T> = TableImpl(paginator, selector, actions, columnsOf())

@JvmSynthetic
inline fun <T> tableOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): Table<T> = TableImpl(paginator, selector, actionsOf(selector) {}, columnsOf())

/*
 * DEAR DEVELOPER,
 * Do not mark this class as inline, because it tends to increase bundle size
 * due to very long columns declarations that can be found in complex tables
 */
@JvmSynthetic
inline fun <T> simpleTableOf(
    items: Collection<T>,
    noinline columns: ColumnsBuilder<T>.() -> Unit
): Table<T> {
    val paginator = SinglePagePaginator(items)
    paginator.loadFirstPage()
    val selector = SelectionManager(paginator)
    return TableImpl(paginator, selector, actionsOf(), columnsOf(emptyList(), columns))
}