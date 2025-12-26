package symphony.internal

import cinematic.mutableLiveOf
import kevlar.action0
import symphony.FixedActionsBuilder
import symphony.FixedActionsManager
import symphony.Mover

@PublishedApi
internal class FixedActionsManagerImpl(
    private val builder: FixedActionsBuilder
) : FixedActionsManager {
    override val current get() = mutableLiveOf(get())

    override fun get() = builder.buildActions()

    override fun add(name: String, handler: () -> Unit): FixedActionsManager {
        if (find(name) != null) return this
        val action = action0(name, handler = handler)
        builder.extraActions[action.key] = action
        return this
    }

    override fun remove(key: String): FixedActionsManager {
        if (find(key) == null) return this
        builder.filters.add(key.lowercase())
        current.value = builder.buildActions()
        return this
    }

    override fun find(name: String) = builder.buildActions().find { it.name.contains(name, ignoreCase = true) }

    inner class ActionsMoverImpl(private val column: String) : Mover {

        override suspend fun at(index: Int) {
            val old = find(column) ?: return
            return TODO()
        }

        override suspend fun before(name: String) {
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
            return TODO()
//            if (subject.index <= anchor.index) return
//            return at(anchor.index)
        }

        override suspend fun after(name: String) {
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
            return TODO()
//            if (subject.index >= anchor.index + 1) return
//            return at(anchor.index + 1)
        }
    }

    override fun move(name: String): Mover = ActionsMoverImpl(name)

    override fun refresh() {
        current.value = builder.buildActions()
    }
}