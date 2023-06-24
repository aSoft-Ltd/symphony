package symphony

import kommander.expect
import kommander.expectFailure
import koncurrent.Later
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import lexi.Logger
import symphony.tools.CompanyInput
import symphony.tools.CompanyOutput
import symphony.tools.PersonInput
import symphony.tools.PersonOutput
import symphony.tools.setInvalidValues
import symphony.tools.setAllValidValues
import symphony.tools.setRequiredValidValues
import symphony.tools.toBeInValidAndHaveInValidValues
import symphony.tools.toBeValidWithAllValuesSet
import symphony.tools.toBeValidWithRequiredValuesOnly
import kotlin.test.Test

class LargeInputAsFieldTest {

    @Test
    fun should_be_fail_to_submit_a_field_with_validation_errors() = runTest {
        var company: CompanyOutput? = null
        Logger()
        val form = CompanyInput().toForm(
            heading = "Company Form",
            details = "Test company form",
            config = SubmitConfig()
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
    fun should_be_able_to_submit_a_field_with_all_values_set_and_valid() = runTest {
        var company: CompanyOutput? = null
        Logger()
        val form = CompanyInput().toForm(
            heading = "Company Form",
            details = "Test company form",
            config = SubmitConfig()
        ) {
            onSubmit {
                company = it
                Later(it)
            }
        }

        form.input.fields.director.fields.setAllValidValues()
        form.submit().await()
        expect(company).toBeNonNull()
        expect(form.input.fields.director.fields).toBeValidWithAllValuesSet()
    }

    @Test
    fun should_be_able_to_submit_a_field_with_only_required_values_set_and_valid() = runTest {
        var company: CompanyOutput? = null
        Logger()
        val form = CompanyInput().toForm(
            heading = "Company Form",
            details = "Test company form",
            config = SubmitConfig()
        ) {
            onSubmit {
                company = it
                Later(it)
            }
        }

        form.input.fields.director.fields.setRequiredValidValues()
        form.submit().await()
        expect(company).toBeNonNull()
        expect(form.input.fields.director.fields).toBeValidWithRequiredValuesOnly()
    }
}