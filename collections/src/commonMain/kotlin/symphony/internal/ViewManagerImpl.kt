package symphony.internal

import cinematic.mutableLiveOf
import symphony.ViewManager

@PublishedApi
internal class ViewManagerImpl<T>(
    private val views: MutableSet<T>,
    private val callback: (suspend (T) -> Unit)? = null,
) : ViewManager<T> {

    override val current = mutableLiveOf(views.first())

    override fun all() = views.toList()

    override fun add(view: T): ViewManager<T> {
        views.add(view)
        current.value = current.value
        return this
    }

    override fun remove(view: T): ViewManager<T> {
        views.remove(view)
        current.value = current.value
        return this
    }

    override suspend fun select(view: T) {
        current.value = view
        callback?.invoke(view)
    }
}