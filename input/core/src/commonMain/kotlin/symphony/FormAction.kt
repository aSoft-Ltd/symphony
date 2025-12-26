@file:JsExport

package symphony

import kotlinx.JsExport

class FormAction(
    val label: Label,
    val handler: suspend () -> Unit
)