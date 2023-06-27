@file:JsExport

package symphony

import kotlin.js.JsExport

interface BooleanField : BaseField<Boolean> {
    fun toggle()
}