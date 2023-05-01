package symphony.fields.text

import kommander.expect
import cinematic.expect
import cinematic.toHaveGoneThrough6
import symphony.TextInputField
import symphony.InputFieldState.Empty
import symphony.InputFieldState.Warning
import kotlin.test.Test

class TextInputFeedbackTest {
    @Test
    fun should_warn_without_throwing_while_typing_input() {
        val field = TextInputField(name = "Test", minLength = 3, maxLength = 4)
        field.type("Anders")
        val (w1, w2, _, _, w5, w6) = expect(field.feedback).toHaveGoneThrough6<Warning, Warning, Empty, Empty, Warning, Warning>()
        listOf(w1, w2).forEach {
            expect(it.message).toBe("Test must have more than 3 characters")
        }

        listOf(w5, w6).forEach {
            expect(it.message).toBe("Test must have less than 4 characters")
        }
    }
}