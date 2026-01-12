package symphony

data class MultiSelectedChoice<out T>(
    val items: List<T>,
    val options: List<Option>,
    val values: Set<String>
)