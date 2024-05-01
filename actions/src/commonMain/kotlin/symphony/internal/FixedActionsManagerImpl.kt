package symphony.internal

import cinematic.mutableLiveOf
import kevlar.Action
import kevlar.action0
import kollections.List
import kollections.set
import koncurrent.Later
import koncurrent.TODOLater
import symphony.SelectorBasedActionsManager
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
        val action = action0(name, handler = handler)
        builder.extraActions[action.key] = action
        return this
    }

    override fun remove(key: String): FixedActionsManager {
        builder.filters.add(key.lowercase())
        current.value = builder.buildActions()
        return this
    }

    override fun find(name: String): Action<Any> {
        TODO("Not yet implemented")
    }

    inner class ActionsMoverImpl(private val column: String) : Mover {

        override fun at(index: Int): Later<Any> {
//            val old = find(column) ?: return
            return TODOLater()
        }

        override fun before(name: String): Later<Any> {
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
            return TODOLater()
//            if (subject.index <= anchor.index) return
//            return at(anchor.index)
        }

        override fun after(name: String): Later<Any> {
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
            return TODOLater()
//            if (subject.index >= anchor.index + 1) return
//            return at(anchor.index + 1)
        }
    }

    override fun move(name: String): Mover = ActionsMoverImpl(name)

    override fun refresh() {
        current.value = builder.buildActions()
    }
}