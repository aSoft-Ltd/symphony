@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "UNCHECKED_CAST")

package symphony

import cinematic.Live
import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch
import symphony.properties.Clearable
import symphony.properties.Hideable
import symphony.properties.Resetable
import kotlinx.JsExport

interface Form<out R, out O : Any, out F : Fields<O>> : FormInfo, Resetable, Clearable, Hideable {

    val state: Live<FormState<O, R>>

    val fields: F

    val actions: FormActions

    fun exit()

    fun submit(): Later<R>
}