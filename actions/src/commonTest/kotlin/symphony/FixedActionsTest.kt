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
}