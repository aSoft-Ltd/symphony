package symphony

import keep.Cacheable

interface CollectionScopeConfig<T, out A> : Cacheable {
    val actions: CollectionActionsBuilder<T>.() -> Unit
    val columns: ColumnsBuilder<T>.() -> Unit
}