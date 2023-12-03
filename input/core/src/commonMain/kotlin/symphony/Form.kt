@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "UNCHECKED_CAST")

package symphony

import cinematic.Live
import koncurrent.Later
import symphony.properties.Clearable
import symphony.properties.Hideable
import symphony.properties.Resetable
import kotlin.js.JsExport

interface Form<out R, out O : Any, out F : Fields<O>> : FormInfo, Resetable, Clearable, Hideable {

    val state: Live<FormState<O, R>>

    val fields: F

    val actions: FormActions

    fun exit()

    fun submit(): Later<R>
}