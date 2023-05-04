package symphony.internal

import symphony.CollectionSceneConfig
import keep.Cache
import symphony.SelectorBasedActionsBuilder
import symphony.ColumnsBuilder

@PublishedApi
internal class CollectionSceneConfigImpl<T>(
    override val actions: SelectorBasedActionsBuilder<T>.() -> Unit,
    override val columns: ColumnsBuilder<T>.() -> Unit,
    override val cache: Cache
) : CollectionSceneConfig<T>