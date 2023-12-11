@file:JsExport

package symphony

import kotlinx.JsExport

interface BooleanField : BaseField<Boolean> {
    fun toggle()
}