@file:JsExport
package symphony

import cinematic.Live
import kotlinx.JsExport

interface ViewManager<out T> {
    val current: Live<T>

    fun all(): List<T>

    fun add(view: @UnsafeVariance T): ViewManager<T>

    suspend fun select(view: @UnsafeVariance T)

    fun remove(view: @UnsafeVariance T): ViewManager<T>
}