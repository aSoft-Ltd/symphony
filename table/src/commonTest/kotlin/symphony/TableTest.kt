package symphony

import kollections.listOf
import kommander.expect
import koncurrent.later.await
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class TableTest {
    @Test
    fun can_be_assigned_a_paginator() = runTest {
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)
        val table = tableOf(paginator, selector, emptyActions(), Person.columns())
        println(table.renderToString())

        paginator.refreshAllPages()
        println(table.renderToString())

        paginator.loadNextPage()
        println(table.renderToString())

        paginator.loadNextPage()
        println(table.renderToString())
    }

    @Test
    fun should_be_able_to_select_table_items() = runTest {
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)
        val table = tableOf(paginator, selector, emptyActions(), Person.columns())
        paginator.loadFirstPage()
        println(table.renderToString())

        selector.select(row = 1)
        selector.select(row = 1)
        expect(selector.isCurrentPageSelectedPartially()).toBe(true, "Table was supposed to be partially selected")
        println(table.renderToString())
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() = runTest {
        val paginator = linearPaginatorOf<Person>(10)
        paginator.initialize { Person.List.paged(no, capacity) }.await()
        val selector = selectorOf(paginator)
        val table = tableOf(paginator, selector, emptyActions(), Person.columns())

        paginator.loadFirstPage()
        println(table.renderToString())

        selector.selectAllItemsInTheCurrentPage()
        println(table.renderToString())
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

//    @Test
//    fun should_be_able_to_retrieve_primary_actions() {
//        val paginator = linearPaginatorOf(Person.List)
//        val selector = selectorOf(paginator)
//        val actions = actionsOf(selector) {
//            primary {
//                on("Create Person") { println("Just create da nigga") }
//            }
//
//            single {
//                on("View ${it.name}") { println("Now viewing ${it.name}") }
//            }
//        }
//        val table = tableOf(paginator, selector, actions, Person.columns())
//
//        paginator.loadFirstPage()
//
//        expect(table.actions.get().size).toBe(1)
//
//        selector.select(1)
//        println(table.renderToString())
//
//        expect(table.actions.get().size).toBe(2)
//    }

    @Test
    fun should_be_able_to_show_off() = runTest {
        val people = listOf(
            Person("John", 21),
            Person("Jane", 22),
            Person("Jake", 18),
            Person("Jill", 23),
        )
        val paginator = linearPaginatorOf<Person>(4)
        paginator.initialize { people.paged(no, capacity) }.await()

        val table = tableOf(paginator) {
            selectable()
            column("No") { it.number.toString() }
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }

        println(table.renderToString(4))
    }
}