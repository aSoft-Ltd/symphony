package symphony

import kommander.expect
import kotlin.test.Test

class TextFieldTest {

    @Test
    fun should_be_able_to_change_visibility_and_notify_listeners_at_the_same_time() {
        var notifyCount = 0
        val tf = TextField(name = "tf")
        tf.state.watch { notifyCount++ }
        tf.hide()
        expect(notifyCount).toBe(1)
        expect(tf.hidden).toBe(true)
    }

    @Test
    fun should_be_able_to_be_reset_to_its_original_values_and_clean() {
        val tf = TextField(name = "tf", value = "initial")
        expect(tf.output).toBe("initial")
        tf.set("peter")
        expect(tf.output).toBe("peter")
        tf.reset()
        expect(tf.output).toBe("initial")
        tf.clear()
        expect(tf.output).toBe("")
    }

    @Test
    fun should_have_a_clean_up_method_to_stop_all_current_watchers() {
        val tf = TextField("tf")
    }
}