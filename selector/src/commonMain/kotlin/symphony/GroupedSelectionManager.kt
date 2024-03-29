@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport
import symphony.selected.GroupedSelected

interface GroupedSelectionManager<out G, out T> : SelectionManager<T, GroupedSelected<G, T>>