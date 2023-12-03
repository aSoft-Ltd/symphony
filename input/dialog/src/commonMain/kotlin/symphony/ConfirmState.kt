@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kase.ExecutorState
import kotlin.js.JsExport

sealed interface ConfirmState<out S> {
    val visibility
        get() = when (this) {
            is HiddenConfirmState -> HiddenVisibility
            is VisibleConfirmState -> VisibleVisibility
        }
    val data get() = this as? VisibleConfirmState
}

data object HiddenConfirmState : ConfirmState<Nothing>

interface VisibleConfirmState<out S> : ConfirmState<S> {
    val heading: String
    val details: String
    val message: String
    val subject: S
    val phase: ExecutorState<Unit>
}