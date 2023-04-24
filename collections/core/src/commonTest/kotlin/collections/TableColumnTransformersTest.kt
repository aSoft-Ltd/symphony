package collections

import expect.expect
import presenters.collections.*
import kotlin.test.Test

class TableColumnTransformersTest {
    @Test
    fun should_be_able_to_filter_out_columns_after_the_table_has_been_created() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, Person.columns())
        table.loadFirstPage()
        println(table.renderToString())
        expect(table.columns.all()).toBeOfSize(4)
        table.columns.remove("name")
        println(table.renderToString())
        expect(table.columns.current.value).toBeOfSize(3)
    }

    @Test
    fun should_be_able_to_filter_out_columns_after_the_table_has_been_created_using_the_manage_columns() {
        val paginator = CollectionPaginator(Person.List)
        val selector = SelectionManager(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, Person.columns()).manageColumns { columns ->
            columns.add("Nick Name") {
                it.item.name + it.item.age
            }
        }
        table.loadFirstPage()
        println(table.renderToString())
        expect(table.columns.all()).toBeOfSize(5)
    }
}