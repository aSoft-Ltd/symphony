package symphony.fields.numbers

import kommander.expect
import symphony.DoubleField
import symphony.IntField
import kotlin.test.Test

class NumberInputFormatterTest {
    @Test
    fun should_be_able_to_leave_numbers_as_they_are_when_the_applied_format_has_nothing_to_do() {
        val number = DoubleField(name = "price")
        number.set("100")
        expect(number.output).toBe(100.0)
//        with(number.output) {
//            expect(input).toBe("100")
//            expect(formatted).toBe("100")
//            expect(output).toBe(100.0)
//        }
    }

    @Test
    fun should_be_able_to_format_thousands_as_they_are_typed_in() {
        val number = IntField(
            name = "price"
        )
        number.set("1000")
//        with(number.data.value) {
//            expect(input).toBe("1000")
//            expect(formatted).toBe("1,000")
//            expect(output).toBe(1000)
//        }
//
//        number.type("0")
//        with(number.data.value) {
//            expect(input).toBe("10000")
//            expect(formatted).toBe("10,000")
//            expect(output).toBe(10_000)
//        }
    }
}