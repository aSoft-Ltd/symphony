package symphony

import kollections.size
import kommander.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LinearListTest {

    @Test
    fun can_be_assigned_a_paginator() = runTest {
        val paginator = linearPaginatorOf<Person>()
        paginator.initialize { Person.List.paged(no, capacity) }.await()

        val list = lazyListOf(paginator)

        list.paginator.refreshAllPages().await()

        list.paginator.loadNextPage().await()

        list.paginator.loadNextPage().await()
    }

    @Test
    fun should_be_able_to_select_table_items() = runTest {
        val paginator = linearPaginatorOf<Person>()
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        val list = lazyListOf(paginator)

        list.paginator.loadFirstPage().await()

        selector.select(row = 1)
        selector.select(row = 1)
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() = runTest {
        val paginator = linearPaginatorOf<Person>()
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)

        val list = lazyListOf(paginator)

        list.paginator.loadFirstPage().await()

        selector.selectAllItemsInTheCurrentPage()

        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_retrieve_primary_actions() = runTest {
        val paginator = linearPaginatorOf<Person>()
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)
        val actions = actionsOf(linear = selector) {
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

        list.paginator.loadFirstPage().await()
        selector.select(row = 1)

        expect(actions.get().size).toBe(2)
    }

    @Test
    fun should_be_able_to_load_more_data() = runTest {
        val paginator = linearPaginatorOf<Person>(6)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val list = lazyListOf(paginator)

        list.paginator.loadFirstPage().await()
        expect(list.rows.size).toBe(6)

        list.paginator.loadNextPage().await()
        expect(list.rows.size).toBe(12)

//        list.paginator.refreshAllPages()
//        expect(list.rows.size).toBe(12)

        list.paginator.loadNextPage().await()
        expect(list.rows.size).toBe(18)
    }
}