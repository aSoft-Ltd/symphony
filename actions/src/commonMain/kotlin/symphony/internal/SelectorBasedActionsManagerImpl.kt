package symphony.internal

import kollections.List
import symphony.SelectorBasedActionsManager
import symphony.SelectorBasedActionsBuilder
import symphony.SelectionManager

@PublishedApi
internal class SelectorBasedActionsManagerImpl<T>(
    private val selector: SelectionManager<T>,
    private val builder: SelectorBasedActionsBuilder<T>
) : SelectorBasedActionsManager<T> {
    override val current = selector.selected.map {
        builder.buildActions(it)
    }

    override fun get() = current.value

    override fun add(name: String, handler: () -> Unit): SelectorBasedActionsManager<T> {
        builder.primary { on(name, handler = handler) }
        return this
    }

    override fun addSingle(name: String, handler: (T) -> Unit): SelectorBasedActionsManager<T> {
        builder.single { on(name) { handler(it) } }
        return this
    }

    override fun addMulti(name: String, handler: (List<T>) -> Unit): SelectorBasedActionsManager<T> {
        builder.multi { on(name) { handler(it) } }
        return this
    }

    override fun remove(key: String): SelectorBasedActionsManager<T> {
        builder.filters.add(key.lowercase())
        return this
    }

    override fun of(item: T) = builder.buildSingleSelectActions(item)
}