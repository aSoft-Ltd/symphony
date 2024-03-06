package symphony

import kollections.get
import kollections.value
import kommander.expect
import kommander.toBe
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import symphony.selected.SelectedItem
import symphony.selected.SelectedNone
import kotlin.test.Ignore

class SelectorTest {

    @Test
    fun should_select_a_row_by_number() = runTest {
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()

        selector.select(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true)
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false)

        selector.select(row = 2)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 was selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 was not selected")
    }

    @Test
    fun should_select_multiple_rows_by_number() = runTest {
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()

        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()

        selector.addSelection(1)
        selector.addSelection(2)
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 was not selected")
    }

    @Test
    @Ignore
    fun should_select_multiple_rows_by_number_from_different_pages() = runTest {
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage().await()
        selector.addSelection(1)
        selector.addSelection(2)
        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(true, "Row 2 was not selected")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(true, "Row 1 was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 was not selected")
    }

    @Test
    fun should_be_able_to_clear_selection_of_an_item() = runTest{
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.select(1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 was not selected")

        selector.unSelectRowInCurrentPage(1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was selected")
    }

    @Test
    @Ignore
    fun should_be_able_to_clear_selection_of_the_current_page_only() = runTest{
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 1: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 1: was not selected")

        paginator.loadNextPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(2)
        selector.addSelection(1)
        selector.addSelection(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")

        selector.unSelectAllItemsInTheCurrentPage()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(true, "Row 2 / Page 1: Was not selected")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(true, "Row 1 / Page 1: Was not selected")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was selected")
    }

    @Test
    @Ignore
    fun should_be_able_to_clear_selection_from_all_pages() = runTest{
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(2)
        selector.addSelection(1)
        selector.addSelection(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")

        selector.unSelectAllItemsInAllPages()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(false, "Row 2 / Page 1: Was still selected after deselection")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(false, "Row 1 / Page 1: Was still selected after deselection")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was selected")
    }

    @Test
    @Ignore
    fun should_be_able_to_select_all_items_in_the_current_page() = runTest{
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was already selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was already selected")

        selector.selectAllItemsInTheCurrentPage()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(false, "Row 2 / Page 1: Was still selected after deselection")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(false, "Row 1 / Page 1: Was still selected after deselection")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")
    }

    @Test
    fun should_be_able_to_select_all_items_from_all_pages() = runTest{
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.addSelection(1)
        selector.addSelection(2)

        paginator.loadNextPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(2)

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(false, "Row 2 / Page 2: was already selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was already selected")

        selector.selectAllItemsInAllPages()

        expect(selector.isRowSelectedOnPage(row = 2, page = 1)).toBe(true, "Row 2 / Page 1: Was still selected after deselection")
        expect(selector.isRowSelectedOnPage(row = 1, page = 1)).toBe(true, "Row 1 / Page 1: Was still selected after deselection")

        expect(selector.isRowSelectedOnCurrentPage(row = 2)).toBe(true, "Row 2 / Page 2: was not selected")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was not selected")
    }

    @Test
    fun should_be_able_to_toggle_selection_of_current_page() = runTest{
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.toggleSelectionOfRowInCurrentPage(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was supposed to be selected")

        selector.toggleSelectionOfRowInCurrentPage(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(false, "Row 1 / Page 2: was supposed to not be selected")
    }

    @Test
    fun should_be_able_to_get_the_selected_item() = runTest{
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        selector.toggleSelectionOfRowInCurrentPage(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Row 1 / Page 2: was supposed to be selected")

        expect(selector.selected.value).toBe<SelectedItem<Person>>()
    }

    @Test
    fun should_be_able_to_select_by_object_instance() = runTest{
        val paginator = linearPaginatorOf<Person>(5)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(1)

        paginator.loadNextPage().await()
        expect(paginator.currentPageOrNull?.number).toBe(2)

        selector.select(Person.List[3])
        expect(selector.isRowSelectedOnPage(row = 4, page = 1)).toBe(true, "Row 4 / Page 1: was supposed to be selected")

        expect(selector.selected.value).toBe<SelectedItem<Person>>()
    }

    @Test
    fun should_have_zero_actions_after_all_rows_in_page_have_been_unselected() = runTest{
        val paginator = linearPaginatorOf<Person>(5)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        paginator.loadFirstPage().await()
        repeat(4) {
            selector.addSelection(row = it + 1)
        }

        repeat(4) {
            expect(selector.isRowSelectedOnPage(row = it + 1, page = 1)).toBe(true, "Row ${it + 1} / Page 1: was supposed to be selected")
        }

        selector.unSelectAllItemsInTheCurrentPage()

        expect(selector.selected.value).toBe<SelectedNone>()
    }
}