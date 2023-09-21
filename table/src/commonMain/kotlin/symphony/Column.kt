@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

sealed class Column<in D> : Comparable<Column<Any>> {
    abstract val name: String
    abstract val key: String
    abstract val index: Int
    abstract val visibility: Visibility
    val number get() = index + 1

    class Select(
        override val name: String,
        override val key: String,
        override val index: Int,
        override val visibility: Visibility
    ) : Column<Any?>()

    class Data<in D>(
        override val name: String,
        override val key: String,
        override val index: Int,
        override val visibility: Visibility,
        val default: String,
        val accessor: (Row<D>) -> Any?
    ) : Column<D>() {
        fun resolve(row: Row<D>): String = accessor(row)?.toString() ?: default
    }

    class Action(
        override val name: String,
        override val key: String,
        override val index: Int,
        override val visibility: Visibility
    ) : Column<Any?>()

    val asSelect get() = this as? Select
    val asData get() = this as? Data
    val asAction get() = this as? Action

    internal fun copy(
        name: String = this.name,
        index: Int = this.index,
        visibility: Visibility = this.visibility
    ): Column<D> = when (this) {
        is Action -> Action(name, key, index, visibility)
        is Data -> Data(name, key, index, visibility, default, accessor)
        is Select -> Select(name, key, index, visibility)
    }

    override fun toString() = name
    override fun equals(other: Any?): Boolean = other is Column<Nothing> && other.name == name && other.key == key
    override fun compareTo(other: Column<Any>): Int = index.compareTo(other.index)
}