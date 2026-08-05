package symphony

class FoundItem<out T>(
    val item: T,
    val paged: Paged<T>
) {
    val row by lazy {
        val index = paged.items.content.indexOf(item)
        Row(index, item)
    }
}