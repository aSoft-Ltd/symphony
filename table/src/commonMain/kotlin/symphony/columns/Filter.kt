package symphony.columns

sealed interface Filter {

    data object None : Filter
    data class Keyword(val value: List<String>) : Filter {
        constructor(value: String) : this(listOf(value))
    }

    data class Range(val min: String, val max: String) : Filter

    fun toQuery(): String = when (this) {
        is None -> ""
        is Keyword -> value.joinToString(",")
        is Range -> if (min == max) min else "$min-$max"
    }
}