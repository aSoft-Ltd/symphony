@file:JsExport

package symphony

import kotlin.js.JsExport

sealed interface Visibility {

    val isVisible get() = this is VisibleVisibility
    val isHidden get() = this is HiddenVisibility

    fun toggled() = when (this) {
        HiddenVisibility -> VisibleVisibility
        VisibleVisibility -> HiddenVisibility
    }
}