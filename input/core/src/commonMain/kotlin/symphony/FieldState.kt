@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface FieldState<out O> {
    val label: Label
    val output: O?
    val required: Boolean
    val visibility: Visibility
    val feedbacks: Feedbacks
}