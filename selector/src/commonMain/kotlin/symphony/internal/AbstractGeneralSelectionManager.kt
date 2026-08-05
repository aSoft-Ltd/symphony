package symphony.internal

import symphony.Paginator
import symphony.Selector

abstract class AbstractGeneralSelectionManager<T>(private val paginator: Paginator<T>) : Selector<T> {

    override fun selectAllItemsInTheCurrentPage() = selectAllRowsInPage(paginator.state.value.page)

    override fun selectAllItemsInPage(page: Int) = selectAllRowsInPage(page)

    abstract fun selectAllRowsInPage(page: Int?)

    override fun select(row: Int) = selectRow(row, paginator.state.value.page)

    override fun select(row: Int, page: Int) = selectRow(row, page)

    abstract fun selectRow(row: Int, page: Int?)

    override fun select(obj: T) {
        val found = paginator.find(obj) ?: return
        selectRow(row = found.row.number, page = found.paged.page)
    }

    override fun addSelection(row: Int) = addRowSelection(row, paginator.state.value.page)

    override fun addSelection(row: Int, page: Int) = addRowSelection(row, page)

    override fun addSelection(obj: T) {
        val found = paginator.find(obj) ?: return
        addRowSelection(found.row.number, found.paged.page)
    }

    abstract fun addRowSelection(row: Int, page: Int?)

    override fun unSelectRowInCurrentPage(row: Int) = unSelectRowFromPage(row, paginator.state.value.page)

    override fun unSelectRowInPage(row: Int, page: Int) = unSelectRowFromPage(row, page)

    override fun unSelectAllItemsInTheCurrentPage() = unSelectAllRowsInPage(paginator.state.value.page)

    override fun unSelectAllItemsInPage(page: Int) = unSelectAllRowsInPage(page)

    abstract fun unSelectAllRowsInPage(page: Int?)

    abstract fun unSelectRowFromPage(row: Int, page: Int?)

    override fun unSelect(item: T) {
        val found = paginator.find(item) ?: return
        unSelectRowInPage(found.row.number, paginator.state.value.page)
    }

    override fun isRowSelectedOnCurrentPage(row: Int) = isRowItemSelected(row, paginator.state.value.page)

    override fun isRowSelectedOnPage(row: Int, page: Int) = isRowItemSelected(row, page)

    override fun isPageSelectedWholly(page: Int): Boolean = isPageSelectedWithNoExceptions(page)

    override fun isCurrentPageSelectedWholly(): Boolean = isPageSelectedWithNoExceptions(paginator.state.value.page)

    abstract fun isPageSelectedWithNoExceptions(page: Int?): Boolean

    override fun isCurrentPageSelectedPartially(): Boolean = isPageSelectedButPartially(paginator.state.value.page)

    override fun isPageSelectedPartially(page: Int): Boolean = isPageSelectedButPartially(page)

    abstract fun isPageSelectedButPartially(page: Int?): Boolean

    abstract fun isRowItemSelected(row: Int, page: Int?): Boolean

    override fun toggleSelectionOfRowInCurrentPage(row: Int) = toggleRowSelection(row, paginator.state.value.page)

    override fun toggleSelectionOfRowInPage(row: Int, page: Int) = toggleRowSelection(row, page)

    private fun toggleRowSelection(row: Int, page: Int?) = if (isRowItemSelected(row, page)) unSelectRowFromPage(row, page) else select(row)

    override fun toggleSelectionOfCurrentPage() = toggleSelectionOfANullablePage(paginator.state.value.page)

    override fun toggleSelectionOfPage(page: Int) = toggleSelectionOfANullablePage(page)

    private fun toggleSelectionOfANullablePage(page: Int?) = if (isPageSelectedWithNoExceptions(page)) {
        unSelectAllRowsInPage(page)
    } else {
        selectAllRowsInPage(page)
    }
}