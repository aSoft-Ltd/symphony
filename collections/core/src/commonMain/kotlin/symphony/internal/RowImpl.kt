package symphony.internal

import symphony.Row

@PublishedApi
internal data class RowImpl<out D>(
    override val index: Int,
    override val item: D,
) : Row<D> {
    override val number = index + 1
    override fun toString(): String = "Row($number. $item)"
}