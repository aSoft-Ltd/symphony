package symphony

import keep.Cacheable
import symphony.internal.CollectionSceneConfigImpl

inline fun <T> Cacheable.toConfig(): CollectionSceneConfig<T> = CollectionSceneConfigImpl({}, {}, cache)