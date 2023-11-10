@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "OPT_IN_USAGE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kotlin.js.JsExport


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

    val isFistPage: Boolean

    val isLastPage: Boolean
}