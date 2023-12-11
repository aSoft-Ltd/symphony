@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kevlar.Action0
import kollections.List
import cinematic.Live
import kotlinx.JsExport

interface ActionsManager<out T> {
    val current: Live<List<Action0<Unit>>>

    fun get(): List<Action0<Unit>>

    fun add(name: String, handler: () -> Unit): ActionsManager<T>

    fun remove(key: String): ActionsManager<T>
}