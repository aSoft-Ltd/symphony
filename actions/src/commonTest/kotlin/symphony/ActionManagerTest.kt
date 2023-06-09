package symphony

import kommander.expect
import kotlin.test.Test

class ActionManagerTest {

    @Test
    fun should_not_crash_if_there_are_no_current_actions() {
        val pag = SinglePagePaginator(Person.List)
        val sel = SelectionManager(pag)
        val acts = actionsOf(sel) {}
        expect(acts.current.value).toBeEmpty()
    }

    @Test
    fun should_not_crash_if_there_is_a_selected_item_and_there_are_no_current_actions() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        expect(actions.current.value).toBeOfSize(1)
        sel.select(row = 1, page = 1)
        expect(actions.current.value).toBeOfSize(2)
    }

    @Test
    fun should_add_actions_after_table_creations() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        expect(actions.current.value).toBeOfSize(1)
        sel.select(row = 1, page = 1)
        expect(actions.current.value).toBeOfSize(2)
        sel.unSelectAllItemsInAllPages()
        sel.select(row = 1, page = 1)
        expect(actions.current.value).toBeOfSize(2)
    }

    @Test
    fun should_delete_actions_after_table_creations() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onCreate { println("Create things") }
            }

            single {
                onEdit { println("Edit ${it.name}") }
            }
        }
        pag.loadFirstPage()
        sel.select(row = 1, page = 1)
        expect(actions.current.value).toBeOfSize(2)
    }

    @Test
    fun should_only_display_current_multi_actions() {
        val pag = CollectionPaginator(Person.List)
        val sel = SelectionManager(pag)
        val actions = actionsOf(sel) {
            primary {
                onAdd { println("Add Person") }
            }
            multi {
                onDeleteAll(it) { println("Delete ${it.size}") }
            }
        }
        pag.loadFirstPage()
        expect(actions.current.value).toBeOfSize(1)
        sel.addSelection(1)
        sel.addSelection(2)
        expect(actions.current.value).toBeOfSize(2)
        sel.addSelection(3)
        sel.addSelection(4)
        expect(actions.current.value).toBeOfSize(2)
        actions.remove("Add")
        sel.addSelection(5)
        expect(actions.current.value).toBeOfSize(1)
    }
}