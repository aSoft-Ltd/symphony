package symphony.internal

import cinematic.mutableLiveOf
import kollections.List
import symphony.SelectorBasedActionsManager
import symphony.FixedActionsBuilder
import symphony.FixedActionsManager

@PublishedApi
internal class FixedActionsManagerImpl(
    private val builder: FixedActionsBuilder
) : FixedActionsManager {
    override val current = mutableLiveOf(builder.buildActions())

    override fun get() = current.value

    override fun add(name: String, handler: () -> Unit): FixedActionsManager {
        builder.primary { on(name, handler = handler) }
        current.value = builder.buildActions()
        return this
    }

    override fun remove(key: String): FixedActionsManager {
        builder.filters.add(key.lowercase())
        current.value = builder.buildActions()
        return this
    }
}