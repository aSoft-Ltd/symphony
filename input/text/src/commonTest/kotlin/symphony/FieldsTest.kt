package symphony

import kommander.expect
import kotlin.test.Test

class FieldsTest {

    class TestFormFields : Fields<String>("") {
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