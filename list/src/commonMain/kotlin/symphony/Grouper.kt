package symphony

class Grouper<out G, T>(private val builder: (T) -> Pair<T, G>) {
    fun group(data: Collection<T>): List<Chunk<G, Row<T>>> {
        val groups = mutableMapOf<G, MutableList<T>>()
        data.forEach { item ->
            groups.getOrPut(builder(item).second) { mutableListOf() }.add(item)
        }
        return groups.entries.map { (group, items) ->
            Chunk(group, items.mapIndexed { idx, item -> Row(idx, item) })
        }.toList()
    }
}