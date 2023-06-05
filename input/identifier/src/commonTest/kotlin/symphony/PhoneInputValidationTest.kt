package symphony

import geo.Country
import kommander.expect
import kommander.toBe
import kotlinx.coroutines.test.runTest
import symphony.validation.Invalid
import symphony.PhoneInputField
import symphony.validation.Valid
import kotlin.test.Test

class PhoneInputValidationTest {

    @Test
    fun should_fail_validation_if_input_is_not_a_valid_phone_and_is_not_required() = runTest {
        val phone = PhoneInputField("phone", isRequired = false)
        phone.number.set("0")
        val res = expect(phone.validate()).toBe<Invalid>()
        // TODO: Improve the error message here
//        expect(res.cause.message).toBe("Invalid phone: 0")
    }

    @Test
    fun should_fail_validation_if_input_is_not_a_valid_phone_and_is_required() = runTest {
        val phone = PhoneInputField("phone", isRequired = true)
        phone.number.set("0")
        val res = expect(phone.validate()).toBe<Invalid>()
        expect(res.cause.message).toBe("Phone code is required")
    }

    @Test
    fun should_pass_validation_if_input_is_a_valid_phone() = runTest {
        val phone = PhoneInputField("phone")
        phone.code.selectItem(Country.ZA)
        phone.number.set("0752748674")
        expect(phone.validate()).toBe<Valid>()
        expect(phone.output?.toString()).toBe("27752748674")
    }
}