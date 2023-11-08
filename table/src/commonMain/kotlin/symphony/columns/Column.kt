@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.columns

import kotlin.js.JsExport
import symphony.Visibility

sealed class Column<in D> : Comparable<Column<Any>> {
    abstract val name: String
    abstract val key: String
    abstract val index: Int
    abstract val visibility: Visibility
    val number get() = index + 1

    val asSelect get() = this as? SelectColumn
    val asData get() = this as? DataColumn
    val asAction get() = this as? ActionColumn

    internal fun copy(
        name: String = this.name,
        index: Int = this.index,
        visibility: Visibility = this.visibility
    ): Column<D> = when (this) {
        is ActionColumn -> ActionColumn(name, key, index, visibility)
        is DataColumn -> DataColumn(name, key, index, visibility, default, accessor)
        is SelectColumn -> SelectColumn(name, key, index, visibility)
    }

    override fun toString() = name
    override fun equals(other: Any?): Boolean = other is Column<*> && other.name == name && other.key == key
    override fun compareTo(other: Column<Any>): Int = index.compareTo(other.index)
}