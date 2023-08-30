@file:JsExport

package symphony

import kollections.JsExport

sealed class Visibility {
    data object Visible : Visibility()
    data object Hidden : Visibility()

    val isVisible get() = this is Visible
    val isHidden get() = this is Hidden

    fun toggled() = when (this) {
        Hidden -> Visible
        Visible -> Hidden
    }
}