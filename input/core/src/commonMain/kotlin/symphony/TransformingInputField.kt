@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import symphony.validation.Validateable
import kotlin.js.JsExport

interface TransformingInputField<I, O> : InputField, CommonInputProperties, Settable<I>, LiveData<O>, LiveDataFormatted<I, O>, Validateable<O>