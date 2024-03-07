@file:JsExport

package symphony

import cinematic.Live
import kotlinx.JsExport

interface ListOptionManager<T> {
    val current: Live<T>

    fun add(item: T)
    fun remove(item: T)
    fun clear()
}