package symphony

import symphony.columns.Filter
import symphony.columns.Order

data class Tweaks(
    val sort: Pair<String, Order>?,
    val filters: Map<String, Filter>
) {
    fun params() = buildMap {
        val candidates = filters.filterValues {
            it is Filter.Range || (it is Filter.Keyword && it.value.isNotEmpty())
        }.mapValues { (_, it) ->
            it.toQuery()
        }
        putAll(candidates)
        if (sort == null) return@buildMap
        put("sort_by", sort.first)
        put("sort_order", sort.second.name.lowercase())
    }
}