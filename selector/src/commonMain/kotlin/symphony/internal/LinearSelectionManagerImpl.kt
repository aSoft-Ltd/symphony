package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kollections.find
import kollections.keys
import kollections.Collection
import kollections.Set
import kollections.Map
import kollections.MutableMap
import kollections.mutableSetOf
import kollections.add
import kollections.any
import kollections.entries
import kollections.filter
import kollections.first
import kollections.get
import kollections.getOrPut
import kollections.component1
import kollections.component2
import kollections.contains
import kollections.isEmpty
import kollections.isNullOrEmpty
import kollections.key
import kollections.map
import kollections.mapOf
import kollections.mapValues
import kollections.remove
import kollections.setOf
import kollections.size
import kollections.toMutableMap
import kollections.toMutableSet
import kollections.toSet
import kollections.value
import symphony.LinearPage
import symphony.LinearPaginationManager
import symphony.selected.LinearSelected
import symphony.selected.LinearSelectedGlobal
import symphony.selected.LinearSelectedItem
import symphony.selected.LinearSelectedItems
import symphony.selected.LinearSelectedNone
import symphony.LinearSelectionManager
import symphony.Row

class LinearSelectionManagerImpl<T>(
    private val paginator: LinearPaginationManager<T>
) : AbstractSelectionManager<T, LinearSelected<T>>(paginator), LinearSelectionManager<T> {

    override val selected: MutableLive<LinearSelected<T>> = mutableLiveOf(LinearSelectedNone)

    override fun selectAllRowsInPage(page: Int?) {
        val pageNo = page ?: return
        val p = paginator.find(pageNo) ?: return
        selected.value = LinearSelectedItems(
            page = mapOf(p to p.items.toSet())
        )
    }

    override fun selectAllItemsInAllPages() {
        selected.value = LinearSelectedGlobal(setOf())
    }

    override fun unSelectAllItemsInAllPages() {
        selected.value = LinearSelectedNone
    }

    private fun LinearSelectedItems<T>.unSelectAllRowsInPage(page: Int?): LinearSelected<T> {
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        val p = map.keys.find { it.number == page } ?: return this
        map.remove(p)
        return readjustSelectedItems(map)
    }

    override fun unSelectAllRowsInPage(page: Int?) {
        selected.value = when (val s = selected.value) {
            is LinearSelectedNone -> s
            is LinearSelectedItem -> if (s.page.number == page) LinearSelectedNone else s
            is LinearSelectedItems -> s.unSelectAllRowsInPage(page)
            is LinearSelectedGlobal -> LinearSelectedNone
        }
    }

    private fun LinearSelectedItems<T>.isPageSelectedButPartially(page: Int?): Boolean {
        val entry = this.page.find { it.key.number == page } ?: return false
        return entry.key.capacity != entry.value.size
    }

    override fun isPageSelectedButPartially(page: Int?): Boolean = when (val s = selected.value) {
        is LinearSelectedNone -> false
        is LinearSelectedItem -> s.page.number == page
        is LinearSelectedItems -> s.isPageSelectedButPartially(page)
        is LinearSelectedGlobal -> s.exceptions.any { it.page.number == page }
    }

    private fun LinearSelectedItems<T>.isPageSelectedWithNoExceptions(page: Int?): Boolean {
        val entry = this.page.find { it.key.number == page } ?: return false
        return entry.key.capacity == entry.value.size
    }

    override fun isPageSelectedWithNoExceptions(page: Int?): Boolean = when (val s = selected.value) {
        is LinearSelectedNone -> false
        is LinearSelectedItem -> false
        is LinearSelectedItems -> s.isPageSelectedWithNoExceptions(page)
        is LinearSelectedGlobal -> !s.exceptions.any { it.page.number == page }
    }

    private fun LinearSelectedItems<T>.unselectRowFromPage(row: Int, page: Int): LinearSelected<T> {
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        val p = map.keys.find { it.number == page } ?: return this
        val r = map[p]?.find { it.number == row } ?: return this
        map[p]?.remove(r)
        if (map[p].isNullOrEmpty()) map.remove(p)
        return readjustSelectedItems(map)
    }

    private fun readjustSelectedItems(map: Map<LinearPage<T>, Set<Row<T>>>): LinearSelected<T> = when {
        map.isEmpty() -> LinearSelectedNone

        map.size == 1 && map.entries.first().value.size == 1 -> {
            val entry = map.entries.first()
            LinearSelectedItem(entry.key, entry.value.first())
        }

        else -> LinearSelectedItems(map.mapValues { it.value.toSet() })
    }

    override fun unSelectRowFromPage(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is LinearSelectedNone -> s
            is LinearSelectedItem -> if (s.page.number == page && s.row.number == row) LinearSelectedNone else s
            is LinearSelectedItems -> s.unselectRowFromPage(row, pageNo)
            is LinearSelectedGlobal -> {
                val exceptions = s.exceptions.filter { it.page.number == page && it.row.number == row }
                LinearSelectedGlobal(exceptions.toSet())
            }
        }
    }

    private fun LinearSelectedItem<T>.addRowSelection(row: Int, page: Int): LinearSelected<T> {
        val item = paginator.find(row, page) ?: return this
        return LinearSelectedItems(mapOf(item.page to setOf(this.row, item.row)))
    }

    private fun LinearSelectedItems<T>.addRowSelection(row: Int, page: Int): LinearSelected<T> {
        val item = paginator.find(row, page) ?: return this
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        map.getOrPut(item.page) { mutableSetOf() }.add(item.row)
        return LinearSelectedItems(map.mapValues { it.value.toSet() })
    }

    override fun addRowSelection(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is LinearSelectedNone -> paginator.find(row, pageNo)?.toSelectedItem() ?: return
            is LinearSelectedItem -> s.addRowSelection(row, pageNo)
            is LinearSelectedItems -> s.addRowSelection(row, pageNo)
            is LinearSelectedGlobal -> {
                val exceptions = s.exceptions.filter { it.page.number == page && it.row.number == row }
                LinearSelectedGlobal(exceptions.toSet())
            }
        }
    }

    override fun selectRow(row: Int, page: Int?) {
        val p = page ?: return
        val item = paginator.find(row, page = p)?.toSelectedItem() ?: return
        selected.value = item
    }

    override fun isRowItemSelected(row: Int, page: Int?) = when (val s = selected.value) {
        is LinearSelectedNone -> false
        is LinearSelectedItem -> s.row.number == row && s.page.number == page
        is LinearSelectedItems -> s.page.entries.any { (p, rows) -> p.number == page && rows.map { it.number }.contains(row) }
        is LinearSelectedGlobal -> !s.exceptions.any { it.page.number == page && it.row.number == row }
    }
}