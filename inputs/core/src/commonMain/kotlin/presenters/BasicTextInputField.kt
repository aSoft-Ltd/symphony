@file:JsExport

package presenters

import presenters.properties.Settable
import presenters.properties.Typeable
import presenters.validation.Validateable
import kotlin.js.JsExport

@Deprecated("use symphony")
interface BasicTextInputField : InputField, CommonInputProperties, Settable<String>, SerializableLiveData<String>, Validateable<String>, Typeable