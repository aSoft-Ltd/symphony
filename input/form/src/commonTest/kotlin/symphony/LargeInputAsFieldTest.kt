package symphony

import kommander.expect
import kommander.expectFailure
import koncurrent.Later
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import lexi.Logger
import symphony.tools.CompanyInput
import symphony.tools.CompanyOutput
import symphony.tools.setInvalidValues
import symphony.tools.setValidValues
import symphony.tools.toBeInValidAndHaveInValidValues
import symphony.tools.toBeValidAndHaveValidValues
import kotlin.test.Test

class LargeInputAsFieldTest {

    @Test
    fun should_be_fail_to_submit_a_form_with_validation_errors() = runTest {
        var company: CompanyOutput? = null
        val form = CompanyInput().toForm(
            heading = "Company Form",
            details = "Test company form",
            config = FormConfig(Logger())
        ) {
            onSubmit {
                company = it
                Later(0)
            }
        }

        form.input.fields.director.fields.setInvalidValues()
        expectFailure { form.submit().await() }
        expect(company).toBeNull()
        expect(form.input.fields.director.fields).toBeInValidAndHaveInValidValues()
    }

    @Test
    fun should_be_able_to_submit_a_form_without_validation() = runTest {
        var company: CompanyOutput? = null
        val form = CompanyInput().toForm(
            heading = "Company Form",
            details = "Test company form",
            config = FormConfig(Logger())
        ) {
            onSubmit {
                company = it
                Later(it)
            }
        }

        form.input.fields.director.fields.setValidValues()
        form.submit().await()
        expect(company).toBeNonNull()
        expect(form.input.fields.director.fields).toBeValidAndHaveValidValues()
    }
}