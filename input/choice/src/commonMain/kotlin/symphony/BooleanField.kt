@file:JsExport

package symphony

import kotlin.js.JsExport

interface BooleanField<T : Boolean?> : BaseField<T> {
    fun toggle()
}