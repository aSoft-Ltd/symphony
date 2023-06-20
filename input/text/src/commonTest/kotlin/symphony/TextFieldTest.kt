package symphony

import kommander.expect
import neat.max
import neat.required
import kotlin.test.Test

class TextFieldTest {

    var text = ""
    @Test
    fun should_be_able_to_change_visibility_and_notify_listeners_at_the_same_time() {
        var notifyCount = 0
        val tf = TextField(name = ::text)
        tf.state.watch { notifyCount++ }
        tf.hide()
        expect(notifyCount).toBe(1)
        expect(tf.hidden).toBe(true)
    }

    @Test
    fun should_be_able_to_be_reset_to_its_original_values_and_clean() {
        val tf = TextField(name = ::text, value = "initial")
        expect(tf.output).toBe("initial")
        tf.set("peter")
        expect(tf.output).toBe("peter")
        tf.reset()
        expect(tf.output).toBe("initial")
//        tf.clear()
//        expect(tf.output).toBe("")
    }

    @Test
    fun should_be_able_to_validate_text() {
        val tf = TextField(name = ::text) {
            required()
            max(3)
        }

        tf.set("Jane")
        val warnings = tf.feedbacks.warnings
        expect(warnings).toContainElements()
        expect(warnings.first()).toBe("tf should have less than 3 character(s), but Jane has 4 character(s) instead")
    }
}