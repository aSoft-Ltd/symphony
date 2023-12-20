package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kollections.Map
import kollections.Set
import kollections.add
import kollections.any
import kollections.component1
import kollections.component2
import kollections.contains
import kollections.entries
import kollections.filter
import kollections.find
import kollections.first
import kollections.flatMap
import kollections.get
import kollections.getOrPut
import kollections.isEmpty
import kollections.isNullOrEmpty
import kollections.key
import kollections.keys
import kollections.map
import kollections.mapOf
import kollections.mapValues
import kollections.mutableSetOf
import kollections.remove
import kollections.setOf
import kollections.size
import kollections.toKList
import kollections.toList
import kollections.toMutableMap
import kollections.toMutableSet
import kollections.toSet
import kollections.value
import symphony.GroupedPage
import symphony.GroupedPaginationManager
import symphony.GroupedSelectionManager
import symphony.Row
import symphony.selected.GroupedSelected
import symphony.selected.GroupedSelectedGlobal
import symphony.selected.GroupedSelectedItem
import symphony.selected.GroupedSelectedItems
import symphony.selected.GroupedSelectedNone

class GroupedSelectionManagerImpl<G, T>(
    private val paginator: GroupedPaginationManager<G, T>
) : AbstractSelectionManager<T, GroupedSelected<G, T>>(paginator), GroupedSelectionManager<G, T> {

    override val selected: MutableLive<GroupedSelected<G, T>> = mutableLiveOf(GroupedSelectedNone)

    override fun selectAllRowsInPage(page: Int?) {
        val pageNo = page ?: return
        val p = paginator.find(pageNo) ?: return
        selected.value = GroupedSelectedItems(
            page = mapOf(p to p.groups.flatMap { it.items }.toSet())
        )
    }

    override fun selectAllItemsInAllPages() {
        selected.value = GroupedSelectedGlobal(setOf())
    }

    override fun unSelectAllItemsInAllPages() {
        selected.value = GroupedSelectedNone
    }

    private fun GroupedSelectedItems<G, T>.unSelectAllRowsInPage(page: Int?): GroupedSelected<G, T> {
        val map = this.page.mapValues { it.value }.toMutableMap()
        val p = map.keys.find { it.number == page } ?: return this
        map.remove(p)
        return readjustSelectedItems(map)
    }

    override fun unSelectAllRowsInPage(page: Int?) {
        selected.value = when (val s = selected.value) {
            is GroupedSelectedNone -> s
            is GroupedSelectedItem -> if (s.page.number == page) GroupedSelectedNone else s
            is GroupedSelectedItems -> s.unSelectAllRowsInPage(page)
            is GroupedSelectedGlobal -> GroupedSelectedNone
        }
    }

    private fun GroupedSelectedItems<G, T>.isPageSelectedButPartially(page: Int?): Boolean {
        val entry = this.page.find { it.key.number == page } ?: return false
        return entry.key.capacity != entry.value.size
    }

    override fun isPageSelectedButPartially(page: Int?): Boolean = when (val s = selected.value) {
        is GroupedSelectedNone -> false
        is GroupedSelectedItem -> s.page.number == page
        is GroupedSelectedItems -> s.isPageSelectedButPartially(page)
        is GroupedSelectedGlobal -> s.exceptions.any { it.page.number == page }
    }

    private fun GroupedSelectedItems<G, T>.isPageSelectedWithNoExceptions(page: Int?): Boolean {
        val entry = this.page.find { it.key.number == page } ?: return false
        return entry.key.capacity == entry.value.size
    }

    override fun isPageSelectedWithNoExceptions(page: Int?): Boolean = when (val s = selected.value) {
        is GroupedSelectedNone -> false
        is GroupedSelectedItem -> false
        is GroupedSelectedItems -> s.isPageSelectedWithNoExceptions(page)
        is GroupedSelectedGlobal -> !s.exceptions.any { it.page.number == page }
    }

    private fun GroupedSelectedItems<G, T>.unselectRowFromPage(row: Int, page: Int): GroupedSelected<G, T> {
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        val p = map.keys.find { it.number == page } ?: return this
        val r = map[p]?.find { it.number == row } ?: return this
        map[p]?.remove(r)
        if (map[p].isNullOrEmpty()) map.remove(p)
        return readjustSelectedItems(map)
    }

    private fun readjustSelectedItems(map: Map<GroupedPage<G, T>, Set<Row<T>>>): GroupedSelected<G, T> = when {
        map.isEmpty() -> GroupedSelectedNone

        map.size == 1 && map.entries.first().value.size == 1 -> {
            val entry = map.entries.first()
            GroupedSelectedItem(entry.key, entry.value.first())
        }

        else -> GroupedSelectedItems(map.mapValues { it.value.toSet() })
    }

    override fun unSelectRowFromPage(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is GroupedSelectedNone -> s
            is GroupedSelectedItem -> if (s.page.number == page && s.row.number == row) GroupedSelectedNone else s
            is GroupedSelectedItems -> s.unselectRowFromPage(row, pageNo)
            is GroupedSelectedGlobal -> {
                val exceptions = s.exceptions.filter { it.page.number == page && it.row.number == row }
                GroupedSelectedGlobal(exceptions.toSet())
            }
        }
    }

    private fun GroupedSelectedItem<G, T>.addRowSelection(row: Int, page: Int): GroupedSelected<G, T> {
        val item = paginator.find(row, page) ?: return this
        return GroupedSelectedItems(mapOf(item.page to setOf(this.row, item.row)))
    }

    private fun GroupedSelectedItems<G, T>.addRowSelection(row: Int, page: Int): GroupedSelected<G, T> {
        val item = paginator.find(row, page) ?: return this
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        map.getOrPut(item.page) { mutableSetOf() }.add(item.row)
        return GroupedSelectedItems(map.mapValues { it.value.toSet() })
    }

    override fun addRowSelection(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is GroupedSelectedNone -> paginator.find(row, pageNo)?.toSelectedItem() ?: return
            is GroupedSelectedItem -> s.addRowSelection(row, pageNo)
            is GroupedSelectedItems -> s.addRowSelection(row, pageNo)
            is GroupedSelectedGlobal -> {
                val exceptions = s.exceptions.filter { it.page.number == page && it.row.number == row }
                GroupedSelectedGlobal(exceptions.toSet())
            }
        }
    }

    override fun selectRow(row: Int, page: Int?) {
        val p = page ?: return
        val item = paginator.find(row, page = p)?.toSelectedItem() ?: return
        selected.value = item
    }

    override fun isRowItemSelected(row: Int, page: Int?) = when (val s = selected.value) {
        is GroupedSelectedNone -> false
        is GroupedSelectedItem -> s.row.number == row && s.page.number == page
        is GroupedSelectedItems -> s.page.entries.any { (p, rows) -> p.number == page && rows.map { it.number }.contains(row) }
        is GroupedSelectedGlobal -> !s.exceptions.any { it.page.number == page && it.row.number == row }
    }
}