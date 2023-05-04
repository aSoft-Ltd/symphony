package symphony

import keep.Cacheable

interface CollectionSceneConfig<out T> : Cacheable {
    val actions: SelectorBasedActionsBuilder<@UnsafeVariance T>.() -> Unit
    val columns: ColumnsBuilder<@UnsafeVariance T>.() -> Unit
}