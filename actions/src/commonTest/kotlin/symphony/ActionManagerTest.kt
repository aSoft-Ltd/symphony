package symphony

import kollections.size
import kommander.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ActionManagerTest {

    @Test
    fun should_not_crash_if_there_are_no_current_actions() {
        val acts = emptyActions()
        expect(acts.current.value.size).toBe(0)
    }

    @Test
    fun should_not_crash_if_there_is_a_selected_item_and_there_are_no_current_actions() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize {  Person.List.paged(no,capacity)}.await()
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage().await()
        expect(actions.current.value.size).toBe(1)
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(2)
    }

    @Test
    fun should_add_actions_after_table_creations() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize {  Person.List.paged(no,capacity)}.await()
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage().await()
        expect(actions.current.value.size).toBe(1)
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(2)
        sel.unSelectAllItemsInAllPages()
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(2)
    }

    @Test
    fun should_delete_actions_after_table_creations() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize {  Person.List.paged(no,capacity)}.await()
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage().await()
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(2)
    }

    @Test
    fun should_only_display_current_multi_actions() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize {  Person.List.paged(no,capacity)}.await()
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onAdd { println("Add Person") }
            }
            multi {
                onDeleteAll(it) { println("Delete ${it.size}") }
            }
        }
        pag.loadFirstPage().await()
        expect(actions.current.value.size).toBe(1)
        sel.addSelection(1)
        sel.addSelection(2)
        expect(actions.current.value.size).toBe(2)
        sel.addSelection(3)
        sel.addSelection(4)
        expect(actions.current.value.size).toBe(2)
        actions.remove("Add")
        sel.addSelection(5)
        expect(actions.current.value.size).toBe(1)
    }
}