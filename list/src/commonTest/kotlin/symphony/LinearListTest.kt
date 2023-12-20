package symphony

import kollections.size
import kommander.expect
import kotlin.test.Test

class LinearListTest {

    @Test
    fun can_be_assigned_a_paginator() {
        val paginator = linearPaginatorOf(Person.List)

        val list = lazyListOf(paginator)

        list.paginator.refreshAllPages()

        list.paginator.loadNextPage()

        list.paginator.loadNextPage()
    }

    @Test
    fun should_be_able_to_select_table_items() {
        val paginator = linearPaginatorOf(Person.List)
        val selector = selectorOf(paginator)

        val list = lazyListOf(paginator)

        list.paginator.loadFirstPage()

        selector.select(row = 1)
        selector.select(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() {
        val paginator = linearPaginatorOf(Person.List)
        val selector = selectorOf(paginator)

        val list = lazyListOf(paginator)

        list.paginator.loadFirstPage()

        selector.selectAllItemsInTheCurrentPage()

        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_retrieve_primary_actions() {
        val paginator = linearPaginatorOf(Person.List)
        val selector = selectorOf(paginator)
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

        val list = lazyListOf(paginator)

        list.paginator.loadFirstPage()
        selector.select(row = 1)

        expect(actions.get().size).toBe(2)
    }

    @Test
    fun should_be_able_to_load_more_data() {
        val paginator = linearPaginatorOf(Person.List, capacity = 6)
        val list = lazyListOf(paginator)

        list.paginator.loadFirstPage()
        expect(list.rows.size).toBe(6)

        list.paginator.loadNextPage()
        expect(list.rows.size).toBe(12)

//        list.paginator.refreshAllPages()
//        expect(list.rows.size).toBe(12)

        list.paginator.loadNextPage()
        expect(list.rows.size).toBe(18)
    }
}