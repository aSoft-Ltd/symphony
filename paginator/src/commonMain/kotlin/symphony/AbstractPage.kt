@file:JsExport
package symphony

import kotlinx.JsExport

abstract class AbstractPage : Page {

    override val isEmpty by lazy { size == 0 }

    override val hasMore by lazy { !isLastPage }

    override val isFistPage by lazy { number == 1 }

    override val isLastPage by lazy { size < capacity }
}