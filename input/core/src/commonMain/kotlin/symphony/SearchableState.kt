@file:JsExport

package symphony

import kotlinx.JsExport

interface SearchableState {
    val key: String

    val searchBy: SearchBy
}