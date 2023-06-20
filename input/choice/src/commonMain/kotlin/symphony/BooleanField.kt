@file:JsExport

package symphony

import kotlin.js.JsExport

interface BooleanField<T : Boolean?> : PrimitiveField<T> {
    fun toggle()
}