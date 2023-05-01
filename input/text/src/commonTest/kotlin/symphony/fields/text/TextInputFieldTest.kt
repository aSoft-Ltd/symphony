package symphony.fields.text

import kommander.expect
import symphony.TextInputField
import kotlin.test.Test

class TextInputFieldTest {
    @Test
    fun should_be_able_to_be_cleared() {
        val text = TextInputField(name = "text")
        expect(text.data.value.output).toBe(null)
        text.type("Anderson")
        expect(text.data.value.output).toBe("Anderson")
        text.clear()
        expect(text.data.value.output).toBe(null)
    }
}