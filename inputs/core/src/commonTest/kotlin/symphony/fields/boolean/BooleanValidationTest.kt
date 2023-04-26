package symphony.fields.boolean

import kommander.expect
import kommander.toBe
import kotlinx.coroutines.test.runTest
import cinematic.mutableLiveOf
import symphony.BooleanInputField
import symphony.InputFieldState
import symphony.internal.OutputData
import symphony.internal.validators.RequirementValidator
import symphony.validation.Invalid
import symphony.validation.Valid
import kotlin.test.Test

class BooleanValidationTest {

    @Test
    fun can_construct_a_requirement_validator() {
        val validator = RequirementValidator(
            mutableLiveOf(OutputData(1)),
            mutableLiveOf(InputFieldState.Empty),
            "Test", true
        )
        expect(validator).toBeNonNull()
    }

    @Test
    fun can_construct_a_boolean_input_field() {
        val show = BooleanInputField("show")
        expect(show).toBeNonNull()
    }

    @Test
    fun should_fail_validation_if_input_is_required() {
        val switch = BooleanInputField(name = "switch", isRequired = true)
        val res = switch.validate() as Invalid
        expect(res.cause.message).toBe("Switch is required")
    }

    @Test
    fun should_pass_validation_if_input_is_set() = runTest {
        val switch = BooleanInputField(name = "switch")
        switch.set(true)
        val res = switch.validate()
        expect(res).toBe<Valid>()
    }
}