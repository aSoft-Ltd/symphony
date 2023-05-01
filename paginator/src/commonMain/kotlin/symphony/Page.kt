@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "OPT_IN_USAGE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.List
import kotlin.js.JsExport


/**
 * A model representation of what a Page of data should contain
 */
interface Page<out T> {
    /**
     * The items of elements the page contains
     */
    val items: List<Row<T>>

    /**
     * The capacity of the contained items.
     * This will always equal [items].size, except for last pages
     * e.g. The last page can have 7 items, while the page can hold up to 10 items.
     * if ([items].size < [capacity]) is satisfied, one can safely assume that this is the last page.
     */
    val capacity: Int

    /**
     * The page [number] of this whole collection
     */
    val number: Int

    /**
     * Checks to see weather the page is empty
     */
    val isEmpty: Boolean

    val hasMore: Boolean

    val isFistPage: Boolean

    val isLastPage: Boolean

    fun <R> map(transformer: (item: T) -> R): Page<R>

    fun <R> mapIndexed(transformer: (index: Int, item: T) -> R): Page<R>
}