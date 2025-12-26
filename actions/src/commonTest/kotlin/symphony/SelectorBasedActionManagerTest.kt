package symphony

import kommander.expect
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SelectorBasedActionManagerTest {

    @Test
    fun should_be_able_to_dynamically_add_primary_actions() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize { params ->
            Person.List.paged(params)
        }
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }

        actions.add("Test") { println("Test clicked") }

        pag.loadFirstPage()
        expect(actions.current.value.size).toBe(2)
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(3)
    }

    @Test
    fun should_be_able_to_dynamically_add_single_actions() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize { params ->
            Person.List.paged(params)
        }
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }

        actions.addSingle("Test") { println("$it has been clicked for testing") }

        pag.loadFirstPage()
        expect(actions.current.value.size).toBe(1)
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(3)
    }

    @Test
    fun should_be_able_to_dynamically_add_multi_actions() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize { params ->
            Person.List.paged(params)
        }
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }

        actions.addMulti("Test") { println("${it.size} have been clicked for testing") }

        pag.loadFirstPage()
        expect(actions.current.value.size).toBe(1)
        sel.selectAllItemsInTheCurrentPage()
        expect(actions.current.value.size).toBe(2)
        expect(actions.current.value.find { it.name == "Test" }).toBeNonNull()
    }

    @Test
    fun should_not_crash_if_there_are_no_current_actions() {
        val acts = emptyActions()
        expect(acts.current.value.size).toBe(0)
    }

    @Test
    fun should_not_crash_if_there_is_a_selected_item_and_there_are_no_current_actions() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize { params ->
            Person.List.paged(params)
        }
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        expect(actions.current.value.size).toBe(1)
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(2)
    }

    @Test
    fun should_add_actions_after_table_creations() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize { params -> Person.List.paged(params) }
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
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
        pag.initialize { params -> Person.List.paged(params) }
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        sel.select(row = 1, page = 1)
        expect(actions.current.value.size).toBe(2)
    }

    @Test
    fun should_only_display_current_multi_actions() = runTest {
        val pag = linearPaginatorOf<Person>(10)
        pag.initialize { params -> Person.List.paged(params) }
        val sel = selectorOf(pag)
        val actions = actionsOf(linear = sel) {
            primary {
                onAdd { println("Add Person") }
            }
            multi {
                onDeleteAll(it) { println("Delete ${it.size}") }
            }
        }
        pag.loadFirstPage()
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