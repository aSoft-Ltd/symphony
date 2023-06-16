@file:JsExport

package symphony

import symphony.properties.Settable
import symphony.validation.Validateable
import kotlin.js.JsExport

interface BooleanInputField : InputField, CommonInputProperties, Settable<Boolean>, LiveData<Boolean>, Validateable<Boolean> {
    fun toggle()
}