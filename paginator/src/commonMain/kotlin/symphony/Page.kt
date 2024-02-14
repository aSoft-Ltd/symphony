@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "OPT_IN_USAGE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.JsExport


/**
 * A model representation of what a Page of data should contain
 */
interface Page {

    val size: Int

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

    val isFirstPage: Boolean

    val isLastPage: Boolean
}