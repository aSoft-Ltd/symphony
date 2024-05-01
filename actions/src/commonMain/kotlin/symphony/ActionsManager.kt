@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kevlar.Action0
import kollections.List
import cinematic.Live
import kevlar.Action
import kotlinx.JsExport

interface ActionsManager<out T> {
    val current: Live<List<Action0<Unit>>>

    fun get(): List<Action0<Unit>>

    fun add(name: String, handler: () -> Unit): ActionsManager<T>

    fun find(name: String): Action<Any>?

    fun move(name: String) : Mover

    fun remove(key: String): ActionsManager<T>
}