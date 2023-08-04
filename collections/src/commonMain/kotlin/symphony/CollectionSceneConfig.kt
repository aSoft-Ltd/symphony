package symphony

import keep.Cacheable

@Deprecated("do not use this any more")
interface CollectionSceneConfig<out T> : Cacheable {
    val actions: SelectorBasedActionsBuilder<@UnsafeVariance T>.() -> Unit
    val columns: ColumnsBuilder<@UnsafeVariance T>.() -> Unit
}