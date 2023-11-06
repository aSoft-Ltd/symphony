@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface GroupedSelectionManager<G, T> : ISelectionManager<T, GroupedSelected<G, T>>