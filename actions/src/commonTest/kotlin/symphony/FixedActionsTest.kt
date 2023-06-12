package symphony

import kommander.expect
import kotlin.test.Test

class FixedActionsTest {

    @Test
    fun should_initialize_fixed_actions() {
        val a = actionsOf {
            onAdd { println("Added stuf") }
            onEdit { println("Edditing") }
            onCancel { println("Creating") }
            onAddAll { println("Adding all") }
        }
        expect(a.get()).toBeOfSize(4)
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