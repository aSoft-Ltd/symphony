package symphony.internal

import symphony.AbstractGeneralSelectorBasedActionsBuilder
import symphony.GeneralSelectorBasedActionsBuilder
import symphony.GeneralSelectorBasedActionsManager
import symphony.Mover
import symphony.Selector

@PublishedApi
internal abstract class AbstractGeneralSelectorBasedActionsManager<T>(
    private val selector: Selector<T>,
    private var builder: AbstractGeneralSelectorBasedActionsBuilder<T>
) : GeneralSelectorBasedActionsManager<T> {

    override val current = selector.selected.map {
        builder.buildActions(it)
    }

    override fun get() = current.value

    override fun add(name: String, handler: () -> Unit): GeneralSelectorBasedActionsManager<T> {
        builder.primary { on(name, handler = handler) }
        selector.selected.dispatch()
        return this
    }

    override fun redefine(body: GeneralSelectorBasedActionsBuilder<T>.() -> Unit) {
        builder = GeneralSelectorBasedActionsBuilder<T>().apply(body) // as AbstractGeneralSelectorBasedActionsBuilder<T>
        current.dispatch()
    }

    override fun find(name: String) = get().find { it.name.contains(name, ignoreCase = true) }

    inner class ActionsMoverImpl(private val column: String) : Mover {

        override suspend fun at(index: Int) {
            val old = find(column) ?: return
            return TODO()
        }

        override suspend fun before(name: String) {
            return TODO()
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
//            if (subject.index <= anchor.index) return
//            return at(anchor.index)
        }

        override suspend fun after(name: String) {
            return TODO()
//            val anchor = find(name) ?: return
//            val subject = find(column) ?: return
//            if (subject.index >= anchor.index + 1) return
//            return at(anchor.index + 1)
        }
    }

    override fun move(name: String): Mover = ActionsMoverImpl(name)

    override fun addSingle(name: String, handler: (T) -> Unit): GeneralSelectorBasedActionsManager<T> {
        builder.single { on(name) { handler(it) } }
        selector.selected.dispatch()
        return this
    }

    override fun addMulti(name: String, handler: (List<T>) -> Unit): GeneralSelectorBasedActionsManager<T> {
        builder.multi { on(name) { handler(it) } }
        selector.selected.dispatch()
        return this
    }

    override fun remove(key: String): GeneralSelectorBasedActionsManager<T> {
        builder.filters.add(key.lowercase())
        return this
    }

    override fun of(item: T) = builder.buildSingleSelectActions(item).toList()
}