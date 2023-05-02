package symphony

import keep.Cacheable

interface CollectionSceneConfig<out T> : Cacheable {
    val actions: CollectionActionsBuilder<@UnsafeVariance T>.() -> Unit
    val columns: ColumnsBuilder<@UnsafeVariance T>.() -> Unit
}