package symphony

import keep.Cacheable

interface CollectionScopeConfig<T, out A> : Cacheable {
    val actions: SelectorBasedActionsBuilder<T>.() -> Unit
    val columns: ColumnsBuilder<T>.() -> Unit
}