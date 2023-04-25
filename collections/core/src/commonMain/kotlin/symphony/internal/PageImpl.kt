package symphony.internal

import kollections.List
import symphony.Page
import symphony.Row

@PublishedApi
internal data class PageImpl<out T>(
    override val items: List<Row<T>>,
    override val capacity: Int,
    override val number: Int
) : Page<T> {
    override val isEmpty = items.size == 0

    override val hasMore get() = !isLastPage

    override val isFistPage = number == 1

    override val isLastPage = items.size < capacity

    override fun <R> map(transformer: (item: T) -> R) = Page(
        items = items.map { transformer(it.item) }, capacity, number
    )

    override fun <R> mapIndexed(transformer: (index: Int, item: T) -> R) = Page(
        items = items.mapIndexed { index, it -> transformer(index, it.item) }, capacity, number
    )

    override fun toString(): String = buildString {
        appendLine("Page(number = $number, capacity= $capacity")
        items.forEach {
            appendLine("  ${it.number}. ${it.item}")
        }
        appendLine(")")
    }
}