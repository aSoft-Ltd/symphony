@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

data class FieldsState<out O>(
    val output: O,
    val feedbacks: Feedbacks
)