package symphony

import kollections.Set
import kollections.filter
import kollections.get
import kollections.setOf
import kollections.size
import kollections.toList
import kommander.expect
import symphony.columns.Column
import kotlin.test.Test

class ColumnsManagerTest {
    @Test
    fun should_be_able_to_set_up_columns() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        var current: Set<Column<Person>> = setOf()
        val watcher = columns.current.watchEagerly {
            current = it
        }
        expect(current.size).toBe(3)
        columns.add("no") { it.number.toString() }
        expect(current.size).toBe(4)
        watcher.stop()
    }

    @Test
    fun should_be_able_to_hide_some_columns() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        expect(columns.find("name")?.visibility).toBe(VisibleVisibility)
        columns.hide("name")
        expect(columns.find("name")?.visibility).toBe(HiddenVisibility)
        columns.show("name")
        expect(columns.find("name")?.visibility).toBe(VisibleVisibility)
    }

    @Test
    fun hiding_columns_should_not_affect_their_indexing_and_ordering() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        columns.hide("name")
        expect(columns.find("name")?.index).toBe(1)
        columns.show("name")
        expect(columns.find("name")?.index).toBe(1)
        val all = columns.all().toList()
        val col1 = all[1]
        val col2 = all[2]
        expect(col1.name).toBe("name")
        expect(col2.name).toBe("age")
    }

    @Test
    fun should_not_add_a_different_column_even_when_reindexing() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        expect(columns.all().size).toBe(3)
        columns.add("Status") { "Status 1" }
        columns.add("Status") { "Status 2" }
        expect(columns.all().size).toBe(4)
    }

    @Test
    fun indexing_should_propagate_properly() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        columns.index("age", 1)
        expect(columns.all().filter { it.index == 1 }.size).toBe(1)
    }

    @Test
    fun should_move_a_column_before_another() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        columns.move("age").before("name")
        val allColumns = columns.all().toList()
        expect(allColumns.filter { it.index == 1 }.size).toBe(1)
        val age = allColumns[1]
        val name = allColumns[2]
        expect(age.index).toBe(1)
        expect(name.index).toBe(2)
    }

    @Test
    fun should_move_a_column_after_another() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        columns.move("name").after("age")
        val allColumns = columns.all().toList()
        expect(allColumns.filter { it.index == 2 }.size).toBe(1)
        val age = allColumns[1]
        val name = allColumns[2]
        expect(age.index).toBe(1)
        expect(name.index).toBe(2)
    }
}