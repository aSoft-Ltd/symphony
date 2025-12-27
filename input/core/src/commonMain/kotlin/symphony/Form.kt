@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "UNCHECKED_CAST")

package symphony

import cinematic.Live
import kotlinx.JsExport
import symphony.properties.Clearable
import symphony.properties.Hideable
import symphony.properties.Resetable

interface Form<out R, out O : Any, out F : Fields<O>> : FormInfo, Resetable, Clearable, Hideable {

    val state: Live<FormState<O, R>>

    val fields: F

    val actions: FormActions

    fun exit()

    suspend fun submit()
}