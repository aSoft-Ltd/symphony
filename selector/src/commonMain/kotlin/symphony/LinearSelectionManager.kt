@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport
import symphony.selected.LinearSelected

interface LinearSelectionManager<out T> : SelectionManager<T, LinearSelected<T>>