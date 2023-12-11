@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

sealed interface PeekabooState<out T> {
    val visibility
        get() = when (this) {
            is HiddenPeekabooState -> HiddenVisibility
            is VisiblePeekabooState -> VisibleVisibility
        }
    val data: T? get() = null
}

data object HiddenPeekabooState : PeekabooState<Nothing>

data class VisiblePeekabooState<out T>(override val data: T) : PeekabooState<T>