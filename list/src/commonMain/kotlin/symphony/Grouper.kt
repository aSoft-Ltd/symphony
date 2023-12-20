package symphony

import kollections.Collection
import kollections.List
import kollections.MutableList
import kollections.add
import kollections.component1
import kollections.component2
import kollections.entries
import kollections.forEach
import kollections.getOrPut
import kollections.map
import kollections.mapIndexed
import kollections.mutableListOf
import kollections.mutableMapOf
import kollections.toList

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