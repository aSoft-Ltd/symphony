@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import symphony.properties.SettableRange
import symphony.validation.Validateable
import kotlin.js.JsExport

interface TransformingRangeInputField<I, O> : InputField, CommonInputProperties, SettableRange<I>, SerializableLiveFormattedData<I, Range<O>>, Validateable<Range<O>> {
    val start: SerializableLiveFormattedData<I, O>
    val end: SerializableLiveFormattedData<I, O>
}