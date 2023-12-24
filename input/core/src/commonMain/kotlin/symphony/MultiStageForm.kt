@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "UNCHECKED_CAST")

package symphony

import cinematic.Live
import kollections.List
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import symphony.properties.Clearable
import symphony.properties.Hideable
import symphony.properties.Resetable
import kotlinx.JsExport

interface MultiStageForm<out R, out O : Any, S : FormStage> : Resetable, Clearable, Hideable {

    val stages: List<S>

    val state: Live<MultiStageFormStageState<R, O, S>>

    fun exit()

    fun prev(): Later<*>

    fun next(): Later<*>
}