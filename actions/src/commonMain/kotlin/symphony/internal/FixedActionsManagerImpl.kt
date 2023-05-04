package symphony.internal

import cinematic.mutableLiveOf
import kollections.List
import symphony.ActionsManager
import symphony.FixedActionsBuilder
import symphony.SelectorBasedActionsBuilder

@PublishedApi
internal class FixedActionsManagerImpl(
    private val builder: FixedActionsBuilder
) : ActionsManager<Any> {
    override val current = mutableLiveOf(builder.buildActions())

    override fun get() = current.value

    override fun add(name: String, handler: () -> Unit): ActionsManager<Any> {
        builder.primary { on(name, handler = handler) }
        return this
    }

    override fun addSingle(name: String, handler: (Any) -> Unit): ActionsManager<Any> = this

    override fun addMulti(name: String, handler: (List<Any>) -> Unit): ActionsManager<Any> = this

    override fun remove(key: String): ActionsManager<Any> {
        builder.filters.add(key.lowercase())
        return this
    }

    override fun of(item: Any) = current.value
}