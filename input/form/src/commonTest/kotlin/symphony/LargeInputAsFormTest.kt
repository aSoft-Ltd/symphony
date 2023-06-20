package symphony

import kommander.expect
import kommander.expectFailure
import koncurrent.Later
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import lexi.Logger
import symphony.tools.PersonInput
import symphony.tools.PersonOutput
import symphony.tools.setInvalidValues
import symphony.tools.setValidValues
import symphony.tools.toBeInValidAndHaveInValidValues
import symphony.tools.toBeValidAndHaveValidValues
import kotlin.test.Test

class LargeInputAsFormTest {

    @Test
    fun should_be_fail_to_submit_a_form_with_validation_errors() = runTest {
        var person: PersonOutput? = null
        val form = PersonInput().toForm(
            heading = "",
            details = "",
            config = FormConfig(Logger())
        ) {
            onSubmit {
                person = it
                Later(0)
            }
        }

        form.input.fields.setInvalidValues()
        expectFailure { form.submit().await() }
        expect(person).toBeNull()
        expect(form.input.fields).toBeInValidAndHaveInValidValues()
    }

    @Test
    fun should_be_able_to_submit_a_form_without_validation_errors() = runTest {
        var person: PersonOutput? = null
        val form = PersonInput().toForm(
            heading = "",
            details = "",
            config = FormConfig(Logger())
        ) {
            onSubmit {
                person = it
                Later(0)
            }
        }

        form.input.fields.setValidValues()
        form.submit().await()
        expect(person).toBeNonNull()
        expect(form.input.fields).toBeValidAndHaveValidValues()
    }
}