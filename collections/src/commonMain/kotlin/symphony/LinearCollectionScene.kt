@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import keep.Cacheable
import kotlinx.JsExport

abstract class LinearCollectionScene<T>(config: Cacheable) : LazyCollectionScene<T>(config) {

    override val paginator by lazy { linearPaginatorOf<T>() }

    override val selector by lazy { selectorOf(paginator) }

    override val list by lazy { lazyListOf(paginator) }
}