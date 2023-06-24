package symphony

import kommander.expect
import kotlin.test.Test

class TableTest {
    @Test
    fun can_be_assigned_a_paginator() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val table = tableOf(paginator, selector, actionsOf(), Person.columns())
        println(table.renderToString())
        expect(table.paginator.currentPageOrNull?.number).toBe(null)

        paginator.refreshAllPages()
        println(table.renderToString())

        paginator.loadNextPage()
        println(table.renderToString())

        paginator.loadNextPage()
        println(table.renderToString())
    }

    @Test
    fun should_be_able_to_select_table_items() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val table = tableOf(paginator, selector, actionsOf(), Person.columns())
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
    fun should_be_able_to_select_the_whole_current_page() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val table = tableOf(paginator, selector, actionsOf(), Person.columns())

        paginator.loadFirstPage()
        println(table.renderToString())

        selector.selectAllItemsInTheCurrentPage()
        println(table.renderToString())
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_retrieve_primary_actions() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val actions = actionsOf(selector) {
            primary {
                on("Create Person") { println("Just create da nigga") }
            }

            single {
                on("View ${it.name}") { println("Now viewing ${it.name}") }
            }
        }
        val table = tableOf(paginator, selector, actions, Person.columns())

        paginator.loadFirstPage()

        expect(table.actions.get()).toBeOfSize(1)

        selector.select(1)
        println(table.renderToString())

        expect(table.actions.get()).toBeOfSize(2)
    }
}