@file:JsExport

package symphony

import kollections.JsExport

sealed class Visibility {
    object Visible : Visibility()
    object Hidden : Visibility()

    val isVisible get() = this is Visible
    val isHidden get() = this is Hidden

    fun toggled() = when (this) {
        Hidden -> Visible
        Visible -> Hidden
    }
}