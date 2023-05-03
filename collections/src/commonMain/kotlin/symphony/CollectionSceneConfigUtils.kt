@file:Suppress("NOTHING_TO_INLINE")

package symphony

import keep.Cache
import keep.Cacheable
import symphony.internal.CollectionSceneConfigImpl

inline fun <T> Cacheable.toCollectionSceneConfig(
    noinline actions: CollectionActionsBuilder<T>.() -> Unit = {},
    noinline columns: ColumnsBuilder<T>.() -> Unit = {},
    cache: Cache = this.cache
): CollectionSceneConfig<T> = CollectionSceneConfigImpl(actions, columns, cache)

inline fun <T> Cacheable.actions(
    noinline actions: CollectionActionsBuilder<T>.() -> Unit
): CollectionSceneConfig<T> = CollectionSceneConfigImpl(actions, {}, cache)