package symphony

import kollections.size
import kommander.expect
import kotlin.test.Test

class TableColumnTransformersTest {
    @Test
    fun should_be_able_to_filter_out_columns_after_the_table_has_been_created() {
        val paginator = linearPaginatorOf(Person.List)
        val selector = selectorOf(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, Person.columns())
        table.paginator.loadFirstPage()
        println(table.renderToString())
        expect(table.columns.all().size).toBe(4)
    }

    @Test
    fun should_be_able_to_filter_out_columns_after_the_table_has_been_created_using_the_manage_columns() {
        val paginator = linearPaginatorOf(Person.List)
        val selector = selectorOf(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, Person.columns())
        table.columns.add("Nick Name") {
            it.item.name + it.item.age
        }
        table.paginator.loadFirstPage()
        println(table.renderToString())
        expect(table.columns.all().size).toBe(5)
    }

    @Test
    fun should_not_print_hidden_columns() {
        val paginator = linearPaginatorOf(Person.List)
        val selector = selectorOf(paginator)
        val action = actionsOf(selector) {}
        val table = tableOf(paginator, selector, action, Person.columns())

        table.columns.add("Nick Name") {
            it.item.name + it.item.age
        }
        table.columns.hide("name")
        table.paginator.loadFirstPage()
        println(table.renderToString())
    }
}