package symphony

import kollections.List

data class LinearPage<out T>(
    val items: List<Row<T>>,
    override val capacity: Int,
    override val number: Int
) : AbstractPage() {

    override val size by lazy { items.size }

    override fun toString(): String = buildString {
        appendLine("LinearPage(number = $number, size= $size/$capacity)")
        items.forEach {
            appendLine("  ${it.number}. ${it.item}")
        }
    }
}