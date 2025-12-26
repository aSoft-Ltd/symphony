package confirmations

import cinematic.expect
import cinematic.toHaveGoneThrough2
import kase.Executing
import kase.Failure
import kase.Pending
import kase.Success
import kommander.expect
import symphony.ConfirmationBox
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore // In favour of Confirm
class ConfirmationBoxTest {

    @Test
    fun a_confirmation_box_should_start_in_a_pending_state() {
        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?"
        ) {
            onConfirm { 5 }
        }
        expect(box.state).toBeIn(Pending)
    }

    @Test
    fun a_confirmation_box_can_be_driven_to_a_successful_execution() {
        var confirmed = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?"
        ) {
            onConfirm {
                confirmed = true
                true
            }
        }
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Executing, Success<Any?>>()
        expect(confirmed).toBe(true)
    }

    @Test
    fun a_confirmation_box_can_be_driven_to_a_failed_execution() {
        var cancelled = false
        var confirmed = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?"
        ) {
            onCancel {
                cancelled = true
            }
            onConfirm {
                confirmed = true
                throw RuntimeException("Rejecting for fun")
            }
        }
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Executing, Failure<Any?>>()
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(true)
    }

    @Test
    @Ignore
    fun a_confirmation_box_can_be_cancelled() {
        var cancelled = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?"
        ) {
            onCancel {
                cancelled = true
            }
            onConfirm {
                throw RuntimeException("Rejecting for fun")
            }
        }
        expect(cancelled).toBe(false)
        box.exit()
        expect(cancelled).toBe(true)
    }

    @Test
    fun a_confirmation_box_can_be_driven_to_a_failed_execution_with_a_submit_function_that_can_throw() {
        var cancelled = false
        var confirmed = false

        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?"
        ) {
            onCancel {
                cancelled = true
            }
            onConfirm {
                confirmed = true
                throw RuntimeException("Rejecting for fun")
            }
        }
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(false)

        box.confirm()
        expect(box.state).toHaveGoneThrough2<Executing, Failure<Any?>>()
        expect(cancelled).toBe(false)
        expect(confirmed).toBe(true)
    }

    @Test
    fun a_confirm_action_can_execute_the_catch_clause_of_a_later_after_a_failure() {
        val box = ConfirmationBox(
            heading = "Delete George",
            details = "Are you sure you want to delete George?"
        ) {
            onConfirm {
                throw RuntimeException("Rejecting for fun")
            }
        }

        var caught = false
        try {
            box.confirm()
        } catch (it: Throwable) {
            println("Error: ${it.message}")
        }
        println("after catching")
        expect(box.state).toHaveGoneThrough2<Executing, Failure<Any?>>()
        expect(caught).toBe(true)
    }
}