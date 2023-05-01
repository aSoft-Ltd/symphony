package symphony.fields

import kommander.expect
import symphony.Fields
import symphony.name
import symphony.text
import kotlin.test.Test

class FieldsTest {

    class TestFormFields : Fields() {
        val name = name()
        val age = text(name = "age")
    }

    @Test
    fun should_easily_interact_with_text_fields() {
        val fields = TestFormFields()
        val name = fields.name
        val watcher = name.data.watch {
            println("Watcher Value: $it")
        }
        name.type("Anderson")
        watcher.stop()
        expect(name.data.value.output).toBe("Anderson")
    }
}