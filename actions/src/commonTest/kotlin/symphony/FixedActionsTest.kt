package symphony

import kollections.size
import kommander.expect
import kotlin.test.Test

class FixedActionsTest {

    @Test
    fun should_be_able_to_dynamicall_add_an_action() {
        val actions = actionsOf {
            onAdd { println("Added stuf") }
        }
        actions.add("Test") { println("Test clicked") }
        expect(actions.get().size).toBe(2)
    }

    @Test
    fun should_initialize_fixed_actions() {
        val a = actionsOf {
            onAdd { println("Added stuf") }
            onEdit { println("Edditing") }
            onCancel { println("Creating") }
            onAddAll { println("Adding all") }
        }
        expect(a.get().size).toBe(4)
    }

    @Test
    fun should_execute_builder_when_getting_action() {
        var count = 0
        val a = actionsOf {
            count++
            onAdd { println("Added stuf") }
            onEdit { println("Edditing") }
            onCancel { println("Creating") }
            onAddAll { println("Adding all") }
        }
        a.get()
        expect(count).toBe(1)
        a.get()
        expect(count).toBe(2)
    }
}