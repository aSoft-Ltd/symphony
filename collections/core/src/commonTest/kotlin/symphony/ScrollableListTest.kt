package symphony

import kommander.expect
import kotlinx.coroutines.test.runTest
import cinematic.expect
import cinematic.toHaveGoneThrough2
import kase.Loading
import kase.Success
import kotlin.test.Test

class ScrollableListTest {

    @Test
    fun can_be_assigned_a_paginator() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val actions = actionsOf(selector) {}

        val list = scrollableListOf(paginator, selector, actions)

        expect(list.paginator.currentPageOrNull?.number).toBe(null)

        list.paginator.refresh()
        val (loading) = expect(paginator.current).toHaveGoneThrough2<Loading<Any?>, Success<Any?>>()
        expect(loading.message).toBe("Loading")

        list.paginator.loadNextPage()

        list.paginator.loadNextPage()
    }

    @Test
    fun should_be_able_to_select_table_items() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val actions = actionsOf(selector) {}

        val list = scrollableListOf(paginator, selector, actions)

        list.paginator.loadFirstPage()

        list.selector.select(row = 1)
        selector.select(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(list.selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val actions = actionsOf(selector) {}

        val list = scrollableListOf(paginator, selector, actions)

        list.paginator.loadFirstPage()

        list.selector.selectAllItemsInTheCurrentPage()
        expect(list.selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_retrieve_primary_actions() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val actions = actionsOf(selector) {
            primary {
                on("Create Person") { println("Creating Person") }
            }

            single {
                if (!it.name.contains("Admin")) {
                    on("Convert ${it.name} to Admin") { println("Converting ${it.name} into an admin") }
                } else {
                    on("Remove from admins") { println("Remove ${it.name} from the group of admins") }
                }
            }

            multi {
                on("Delete ${it.size} people?") { println("Deleting ${it.size} people") }
            }

            global {
                on("Send Adverts") { println("Send adverts") }
            }
        }

        val list = scrollableListOf(paginator, selector, actions)

        list.paginator.loadFirstPage()
        list.selector.select(row = 1)

        expect(list.actions.get()).toBeOfSize(2)
    }

    @Test
    fun should_be_able_to_load_more_data() = runTest {
        val paginator = CollectionPaginator(Person.List, capacity = 6)
        val selector = SelectionManager(paginator)
        val actions = actionsOf(selector) {}
        val list = scrollableListOf(paginator, selector, actions)

        list.paginator.loadFirstPage()
        expect(list.rows).toBeOfSize(6)

        list.paginator.loadNextPage()
        expect(list.rows).toBeOfSize(12)

        list.paginator.refresh()
        expect(list.rows).toBeOfSize(12)

        list.paginator.loadNextPage()
        expect(list.rows).toBeOfSize(18)
    }
}