@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import symphony.properties.SettableRange
import symphony.validation.Validateable
import kotlin.js.JsExport

interface RangeInputField<O> : InputField, CommonInputProperties, LiveData<Range<O>>, Validateable<Range<O>>, SettableRange<O> {
    val start: LiveData<O>
    val end: LiveData<O>
}