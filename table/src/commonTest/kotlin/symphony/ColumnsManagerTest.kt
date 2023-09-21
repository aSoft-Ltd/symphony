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
}