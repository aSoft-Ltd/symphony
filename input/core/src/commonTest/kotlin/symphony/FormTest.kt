package symphony

import kommander.expect
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import neat.required
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class FormTest {

    data class FieldOutput(
        var name: String? = null,
        var age: Int? = null
    )

    class TestFields : Fields<FieldOutput>(FieldOutput()) {
        val name = field(
            name = output::name,
        ) { required() }

        val age = field(
            name = output::age
        )
    }

    @Test
    fun should_be_able_to_describe_a_form_properly() = runTest {
        val fields = TestFields()
        val form = fields.toForm(
            heading = "My Form",
            details = "This is a form",
        ) {
            onFailure {
                println("Failed with: ${it.message}")
            }

            onCancel {
                println("Cancelling")
            }

            onSubmit {
                println("Submitting : $it")
                delay(1000)
                "Success"
            }
        }

        val watcher = form.state.watchEagerly {
            println("State: $it")
        }

        form.fields.apply {
            name.set("John")
            age.set(25)
        }

        form.submit()
        watcher.stop()
        val phase = form.state.value.phase as SuccessPhase
        expect(phase.result).toBe("Success")
    }

    @Test
    fun should_be_able_to_granularize_errors_properly() = runTest {
        val fields = TestFields()
        val form = fields.toForm(
            heading = "My Form",
            details = "This is a form",
        ) {
            onSubmit {
                delay(5.seconds)
                fields.errors {
                    val age = it.age ?: throw IllegalArgumentException("Age is required")
                    if (age < 18) it::age to "Student is too young"
                }
                "Welcome Aboard"
            }
        }

        val watcher = form.state.watchEagerly {
            println("State: $it")
        }

        form.fields.apply {
            name.set("John")
            age.set(25)
        }

        form.submit()
        watcher.stop()
        val phase = form.state.value.phase as SuccessPhase
        expect(phase.result).toBe("Welcome Aboard")
    }
}