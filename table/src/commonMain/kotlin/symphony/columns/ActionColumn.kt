@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.columns

import kotlin.js.JsExport
import symphony.Visibility

data class ActionColumn(
    override val name: String,
    override val key: String,
    override val index: Int,
    override val visibility: Visibility
) : Column<Any?>()