package symphony

import keep.CacheMock
import keep.CacheMockConfig
import kollections.Set
import kollections.filter
import kollections.get
import kollections.indexOf
import kollections.setOf
import kollections.size
import kollections.toList
import kommander.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import symphony.columns.Column
import kotlin.test.Test

class ColumnsManagerTest {

    @Test
    fun should_remember_default_columns_visibility_on_different_invocations() = runTest {

        val cache = CacheMock(CacheMockConfig("test"))

        val configured = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        configured.initialize().await()

        configured.hide("name").await()

        val accessed = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        accessed.initialize().await()

        val column = accessed.find("name")
        expect(column?.visibility).toBe(HiddenVisibility)
    }

    @Test
    fun should_remember_default_columns_index_on_different_invocations() = runTest {

        val cache = CacheMock(CacheMockConfig("test"))

        val configured = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        configured.initialize().await()

        configured.move("name").after("age").await()

        val accessed = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        accessed.initialize().await()
        val column = accessed.find("name")
        expect(column?.index).toBe(2)
    }

    @Test
    fun should_remember_custom_columns_visibility_on_different_invocations() = runTest {

        val cache = CacheMock(CacheMockConfig("test"))

        val configured = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        configured.initialize().await()
        configured.add("growth") { if (it.item.age < 18) "child" else "adult" }.await()

        configured.hide("growth").await()

        val accessed = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        accessed.add("growth") { if (it.item.age < 18) "child" else "adult" }.await()
        accessed.initialize().await()

        val column = accessed.find("growth")
        expect(column?.visibility).toBe(HiddenVisibility)
    }

    @Test
    fun should_remember_custom_columns_index_on_different_invocations() = runTest {

        val cache = CacheMock(CacheMockConfig("test"))

        val configured = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        configured.add("growth") { if (it.item.age < 18) "child" else "adult" }.await()
        configured.move("growth").before("name").await()
        configured.initialize().await()

        val accessed = columnsOf<Person>(cache) {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }

        accessed.add("growth") { if (it.item.age < 18) "child" else "adult" }.await()
        accessed.initialize().await()

        val column = accessed.find("growth")
        expect(column?.index).toBe(1)
    }

    @Test
    fun should_be_able_to_set_up_columns() = runTest {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age }
        }
        var current: Set<Column<Person>> = setOf()
        val watcher = columns.current.watchEagerly {
            current = it
        }
        expect(current.size).toBe(3)
        columns.add("no") { it.number }.await()
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
    fun should_not_add_a_different_column_even_when_reindexing() = runTest {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        expect(columns.all().size).toBe(3)
        columns.add("Status") { "Status 1" }.await()
        columns.add("Status") { "Status 2" }.await()
        expect(columns.all().size).toBe(4)
    }

    @Test
    fun indexing_should_propagate_properly() {
        val columns = columnsOf<Person> {
            selectable()
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
        }
        columns.move("age").at(1)
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

    @Test
    fun should_not_move_the_column_back_and_forth_when_move_before_is_called_repeatedly() {
        val columns = columnsOf<Person> {
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
            column("growth") { if (it.item.age < 18) "child" else "adult" }
        }

        columns.move("growth").before("name")
        var growth = columns.find("growth")
        expect(columns.all().toList().indexOf(growth)).toBe(0)

        columns.move("growth").before("name")

        growth = columns.find("growth")
        expect(columns.all().toList().indexOf(growth)).toBe(0)
    }

    @Test
    fun should_not_move_the_column_back_and_forth_when_move_after_is_called_repeatedly() {
        val columns = columnsOf<Person> {
            column("name") { it.item.name }
            column("age") { it.item.age.toString() }
            column("growth") { if (it.item.age < 18) "child" else "adult" }
        }

        columns.move("name").after("growth")
        var name = columns.find("name")
        expect(columns.all().toList().indexOf(name)).toBe(2)

        columns.move("name").after("growth")

        name = columns.find("name")
        expect(columns.all().toList().indexOf(name)).toBe(2)
    }
}