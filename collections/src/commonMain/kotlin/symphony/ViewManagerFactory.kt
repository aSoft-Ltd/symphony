package symphony

import kollections.toMutableSet
import kollections.toSet
import symphony.internal.ViewManagerImpl
import kotlin.enums.enumEntries

inline fun <reified V : Enum<V>> viewsOf(
    noinline callback: ((V) -> Unit)? = null,
): ViewManager<V> {
    return ViewManagerImpl(enumEntries<V>().toMutableSet().toSet().toMutableSet(), callback)
}

inline fun <V> viewsOf(
    vararg views: V,
    noinline callback: ((V) -> Unit)? = null,
): ViewManager<V> {
    return ViewManagerImpl(views.toMutableSet().toSet().toMutableSet(), callback)
}