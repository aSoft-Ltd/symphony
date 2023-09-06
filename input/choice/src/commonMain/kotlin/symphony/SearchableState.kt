@file:JsExport

package symphony

import kotlin.js.JsExport

interface SearchableState {
    val key: String

    val searchBy: SearchBy
}