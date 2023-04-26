@file:JsExport

package presenters

import presenters.properties.Settable
import presenters.validation.Validateable
import kotlin.js.JsExport

@Deprecated("use symphony")
interface BooleanInputField : InputField, CommonInputProperties, Settable<Boolean>, SerializableLiveData<Boolean>, Validateable<Boolean> {
    fun toggle()
}