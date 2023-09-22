package symphony

import cinematic.WatchMode
import cinematic.watch
import kollections.Set
import kollections.iSetOf
import kommander.expect
import kotlin.test.Test

class ColumnsManagerTest {
    @Test
    fun should_be_able_to_set_up_columns() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        var current: Set<Column<Person>> = iSetOf()
        val watcher = columns.current.watch(mode = WatchMode.Eagerly) {
            current = it
        }
        expect(current).toBeOfSize(3)
        columns.add("no") { it.number.toString() }
        expect(current).toBeOfSize(4)
        watcher.stop()
    }

    @Test
    fun should_be_able_to_hide_some_columns() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        expect(columns.find("name")?.visibility).toBe(Visibility.Visible)
        columns.hide("name")
        expect(columns.find("name")?.visibility).toBe(Visibility.Hidden)
        columns.show("name")
        expect(columns.find("name")?.visibility).toBe(Visibility.Visible)
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
        val (_, col1, col2) = columns.all().toList()
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
        expect(columns.all()).toBeOfSize(3)
        columns.add("Status") { "Status 1" }
        columns.add("Status") { "Status 2" }
        expect(columns.all()).toBeOfSize(4)
    }

    @Test
    fun indexing_should_propagate_properly() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        columns.index("age", 1)
        expect(columns.all().filter { it.index == 1 }).toBeOfSize(1)
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
        expect(allColumns.filter { it.index == 1 }).toBeOfSize(1)
        val (_, age, name) = allColumns
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
        expect(allColumns.filter { it.index == 2 }).toBeOfSize(1)
        val (_, age, name) = allColumns
        expect(age.index).toBe(1)
        expect(name.index).toBe(2)
    }
}