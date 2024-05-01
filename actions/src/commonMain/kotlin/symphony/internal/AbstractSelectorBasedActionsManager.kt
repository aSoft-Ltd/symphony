package symphony.internal

import kollections.List
import kollections.add
import kollections.find
import kollections.toList
import koncurrent.Later
import koncurrent.TODOLater
import koncurrent.toLater
import symphony.AbstractSelectorBasedActionsBuilder
import symphony.Mover
import symphony.SelectionManager
import symphony.SelectorBasedActionsManager
import symphony.selected.Selected

@PublishedApi
internal abstract class AbstractSelectorBasedActionsManager<T, S : Selected<T>>(
    private val selector: SelectionManager<T, S>,
    private val builder: AbstractSelectorBasedActionsBuilder<T, S>
) : SelectorBasedActionsManager<T> {

    override val current = selector.selected.map {
        builder.buildActions(it)
    }

    override fun get() = current.value

    override fun add(name: String, handler: () -> Unit): SelectorBasedActionsManager<T> {
        builder.primary { on(name, handler = handler) }
        selector.selected.dispatch()
        return this
    }

    override fun find(name: String) = get().find { it.name.contains(name,ignoreCase = true) }

    inner class ActionsMoverImpl(private val column: String) : Mover {

        override fun at(index: Int): Later<Any> {
            val old = find(column) ?: return toLater()
            return TODOLater()
        }

        override fun before(name: String): Later<Any> {
            return TODOLater()
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
//            if (subject.index <= anchor.index) return
//            return at(anchor.index)
        }

        override fun after(name: String): Later<Any> {
            return TODOLater()
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
//            if (subject.index >= anchor.index + 1) return
//            return at(anchor.index + 1)
        }
    }

    override fun move(name: String): Mover = ActionsMoverImpl(name)

    override fun addSingle(name: String, handler: (T) -> Unit): SelectorBasedActionsManager<T> {
        builder.single { on(name) { handler(it) } }
        selector.selected.dispatch()
        return this
    }

    override fun addMulti(name: String, handler: (List<T>) -> Unit): SelectorBasedActionsManager<T> {
        builder.multi { on(name) { handler(it) } }
        selector.selected.dispatch()
        return this
    }

    override fun remove(key: String): SelectorBasedActionsManager<T> {
        builder.filters.add(key.lowercase())
        return this
    }

    override fun of(item: T) = builder.buildSingleSelectActions(item).toList()
}