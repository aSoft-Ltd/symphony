@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.columns

import kotlinx.JsExport
import symphony.Visibility

data class SelectColumn(
    override val name: String,
    override val key: String,
    override val index: Int,
    override val visibility: Visibility
) : Column<Any?>()