package collections

import expect.expect
import presenters.collections.CollectionPaginator
import presenters.collections.SelectionManager
import presenters.collections.actionsOf
import presenters.collections.columnsOf
import presenters.collections.renderToString
import presenters.collections.tableOf
import kotlin.test.Test

class TableTest {
    @Test
    fun can_be_assigned_a_paginator() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val table = tableOf(paginator, selector, actionsOf(), Person.columns())
        println(table.renderToString())
        expect(table.currentPageOrNull?.number).toBe(null)

        table.refresh()
        println(table.renderToString())

        table.loadNextPage()
        println(table.renderToString())

        table.loadNextPage()
        println(table.renderToString())
    }

    @Test
    fun should_be_able_to_select_table_items() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val table = tableOf(paginator, selector, actionsOf(), Person.columns())
        table.loadFirstPage()
        println(table.renderToString())

        table.select(row = 1)
        selector.select(row = 1)
        expect(table.isCurrentPageSelectedPartially()).toBe(true, "Table was supposed to be partially selected")
        println(table.renderToString())
        expect(selector.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Explicit selector failed to select")
        expect(table.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
    }

    @Test
    fun should_be_able_to_select_the_whole_current_page() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val table = tableOf(paginator, selector, actionsOf(), Person.columns())

        table.loadFirstPage()
        println(table.renderToString())

        table.selectAllItemsInTheCurrentPage()
        println(table.renderToString())
        expect(table.isRowSelectedOnCurrentPage(row = 1)).toBe(true, "Implicit selector failed to select")
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

        table.loadFirstPage()

        expect(table.actions.get()).toBeOfSize(1)

        table.select(1)
        println(table.renderToString())

        expect(table.actions.get()).toBeOfSize(2)
    }
}