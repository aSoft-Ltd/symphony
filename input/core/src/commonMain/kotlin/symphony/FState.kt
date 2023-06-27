@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface FState<out O> {
    val output: O?
    val required: Boolean
    val visibility: Visibility
    val feedbacks: Feedbacks
}