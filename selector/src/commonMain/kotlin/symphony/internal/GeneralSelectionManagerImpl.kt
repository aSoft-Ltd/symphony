package symphony.internal

import cinematic.mutableLiveOf
import symphony.Paged
import symphony.Paginator
import symphony.Row
import symphony.Selected
import symphony.SelectedGlobal
import symphony.SelectedItem
import symphony.SelectedItems
import symphony.SelectedNone

class GeneralSelectionManagerImpl<T>(
    private val paginator: Paginator<T>
) : AbstractGeneralSelectionManager<T>(paginator) {

    override val selected by lazy { mutableLiveOf<Selected<T>>(SelectedNone) }

    override fun selectAllRowsInPage(page: Int?) {
        val pageNo = page ?: return
        val p = paginator.find(pageNo) ?: return
        selected.value = SelectedItems(mapOf(p to p.items.content.toRows()))
    }

    private fun <R> List<R>.toRows() = mapIndexed { idx, t -> Row(idx, t) }.toSet()

    override fun selectAllItemsInAllPages() {
        selected.value = SelectedGlobal(setOf())
    }

    override fun unSelectAllItemsInAllPages() {
        selected.value = SelectedNone
    }

    private fun SelectedItems<T>.unSelectAllRowsInPage(page: Int?): Selected<T> {
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        val p = map.keys.find { it.page == page } ?: return this
        map.remove(p)
        return readjustSelectedItems(map)
    }

    override fun unSelectAllRowsInPage(page: Int?) {
        selected.value = when (val s = selected.value) {
            is SelectedNone -> s
            is SelectedItem -> if (s.page.page == page) SelectedNone else s
            is SelectedItems -> s.unSelectAllRowsInPage(page)
            is SelectedGlobal -> SelectedNone
        }
    }

    private fun SelectedItems<T>.isPageSelectedButPartially(page: Int?): Boolean {
        val entry = this.page.entries.find { it.key.page == page } ?: return false
        return entry.key.items.capacity != entry.value.size
    }

    override fun isPageSelectedButPartially(page: Int?): Boolean = when (val s = selected.value) {
        is SelectedNone -> false
        is SelectedItem -> s.page.page == page
        is SelectedItems -> s.isPageSelectedButPartially(page)
        is SelectedGlobal -> s.exceptions.any { it.page.page == page }
    }

    private fun SelectedItems<T>.isPageSelectedWithNoExceptions(page: Int?): Boolean {
        val entry = this.page.entries.find { it.key.page == page } ?: return false
        return entry.key.items.capacity == entry.value.size
    }

    override fun isPageSelectedWithNoExceptions(page: Int?): Boolean = when (val s = selected.value) {
        is SelectedNone -> false
        is SelectedItem -> s.page.items.content.size == 1
        is SelectedItems -> s.isPageSelectedWithNoExceptions(page)
        is SelectedGlobal -> !s.exceptions.any { it.page.page == page }
    }

    private fun SelectedItems<T>.unselectRowFromPage(row: Int, page: Int): Selected<T> {
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        val p = map.keys.find { it.page == page } ?: return this
        val r = map[p]?.find { it.number == row } ?: return this
        map[p]?.remove(r)
        if (map[p].isNullOrEmpty()) map.remove(p)
        return readjustSelectedItems(map)
    }

    private fun readjustSelectedItems(map: Map<Paged<T>, Set<Row<T>>>): Selected<T> = when {
        map.isEmpty() -> SelectedNone

        map.size == 1 && map.entries.first().value.size == 1 -> {
            val entry = map.entries.first()
            SelectedItem(entry.key, entry.value.first())
        }

        else -> SelectedItems(map.mapValues { it.value.toSet() })
    }

    override fun unSelectRowFromPage(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is SelectedNone -> s
            is SelectedItem -> if (s.page.page == page && s.row.number == row) SelectedNone else s
            is SelectedItems -> s.unselectRowFromPage(row, pageNo)
            is SelectedGlobal -> {
                val exceptions = s.exceptions.filter { it.page.page == page && it.row.number == row }
                SelectedGlobal(exceptions.toSet())
            }
        }
    }

    private fun SelectedItem<T>.addRowSelection(row: Int, page: Int): Selected<T> {
        val found = paginator.find(row, page) ?: return this
        return SelectedItems(mapOf(found.paged to setOf(this.row,found.row)))
    }

    private fun SelectedItems<T>.addRowSelection(row: Int, page: Int): Selected<T> {
        val found = paginator.find(row, page) ?: return this
        val map = this.page.mapValues { it.value.toMutableSet() }.toMutableMap()
        map.getOrPut(found.paged) { mutableSetOf() }.add(found.row)
        return SelectedItems(map.mapValues { it.value.toSet() })
    }

    override fun addRowSelection(row: Int, page: Int?) {
        val pageNo = page ?: return
        selected.value = when (val s = selected.value) {
            is SelectedNone -> paginator.find(row, pageNo)?.toSelectedItem() ?: return
            is SelectedItem -> s.addRowSelection(row, pageNo)
            is SelectedItems -> s.addRowSelection(row, pageNo)
            is SelectedGlobal -> {
                val exceptions = s.exceptions.filter { it.page.page == page && it.row.number == row }
                SelectedGlobal(exceptions.toSet())
            }
        }
    }

    override fun selectRow(row: Int, page: Int?) {
        val p = page ?: return
        val item = paginator.find(row, page = p)?.toSelectedItem() ?: return
        selected.value = item
    }

    override fun isRowItemSelected(row: Int, page: Int?) = when (val s = selected.value) {
        is SelectedNone -> false
        is SelectedItem -> s.row.number == row && s.page.page == page
        is SelectedItems -> s.page.entries.any { (p, rows) -> p.page == page && rows.map { it.number }.contains(row) }
        is SelectedGlobal -> !s.exceptions.any { it.page.page == page && it.row.number == row }
    }
}