package symphony.internal

import symphony.CollectionSceneConfig
import keep.Cache
import symphony.CollectionActionsBuilder
import symphony.ColumnsBuilder

@PublishedApi
internal class CollectionSceneConfigImpl<T>(
    override val actions: CollectionActionsBuilder<T>.() -> Unit,
    override val columns: ColumnsBuilder<T>.() -> Unit,
    override val cache: Cache
) : CollectionSceneConfig<T>