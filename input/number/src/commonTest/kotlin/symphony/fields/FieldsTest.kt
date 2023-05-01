package symphony.fields

import kommander.expect
import symphony.double
import symphony.Fields
import kotlin.test.Test

class FieldsTest {

    class TestFormFields : Fields() {
        val age = double(name = "age")
    }

    @Test
    fun should_easily_interact_with_text_fields() {
        val fields = TestFormFields()
        val age = fields.age
        val watcher = age.data.watch {
            println("Watcher Value: $it")
        }
        age.type("55")
        watcher.stop()
        expect(age.data.value.output).toBe(55.0)
    }
}