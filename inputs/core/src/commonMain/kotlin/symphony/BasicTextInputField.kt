@file:JsExport

package symphony

import symphony.properties.Settable
import symphony.properties.Typeable
import symphony.validation.Validateable
import kotlin.js.JsExport

interface BasicTextInputField : InputField, CommonInputProperties, Settable<String>, SerializableLiveData<String>, Validateable<String>, Typeable