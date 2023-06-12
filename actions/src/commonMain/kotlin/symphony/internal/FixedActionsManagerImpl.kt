package symphony.internal

import cinematic.mutableLiveOf
import kevlar.action0
import kollections.List
import symphony.SelectorBasedActionsManager
import symphony.FixedActionsBuilder
import symphony.FixedActionsManager

@PublishedApi
internal class FixedActionsManagerImpl(
    private val builder: FixedActionsBuilder
) : FixedActionsManager {
    override val current get() = mutableLiveOf(get())

    override fun get() = builder.buildActions()

    override fun add(name: String, handler: () -> Unit): FixedActionsManager {
        val action = action0(name, handler = handler)
        builder.extraActions[action.key] = action
        return this
    }

    override fun remove(key: String): FixedActionsManager {
        builder.filters.add(key.lowercase())
        current.value = builder.buildActions()
        return this
    }

    override fun refresh() {
        current.value = builder.buildActions()
    }
}