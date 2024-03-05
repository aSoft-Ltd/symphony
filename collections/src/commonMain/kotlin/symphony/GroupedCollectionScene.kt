@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import keep.Cacheable
import kotlinx.JsExport

abstract class GroupedCollectionScene<T>(config: Cacheable) : LazyCollectionScene<T>(config) {

    override val paginator by lazy { linearPaginatorOf<T>() }

    abstract override val selector: GroupedSelectionManager<*,T>

    abstract override val list: GroupedList<*,T>
}