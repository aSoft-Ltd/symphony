@file:JsExport
package symphony

import kotlin.js.JsExport

interface Searchable {
    fun setSearchKey(key: String?): String

    fun appendSearchKey(key: String?): String

    fun backspaceSearchKey(): String

    fun clearSearchKey(): String

    fun setSearchBy(sb: SearchBy)

    fun setSearchByOrdering()

    fun setSearchByFiltering()

    fun search()
}