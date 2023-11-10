@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport
import symphony.selected.LinearSelected

interface LinearSelectionManager<out T> : SelectionManager<T, LinearSelected<T>>