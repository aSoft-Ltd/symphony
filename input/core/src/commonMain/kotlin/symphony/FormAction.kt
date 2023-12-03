@file:JsExport

package symphony

import kotlin.js.JsExport

class FormAction(
    val label: Label,
    val handler: () -> Unit
)