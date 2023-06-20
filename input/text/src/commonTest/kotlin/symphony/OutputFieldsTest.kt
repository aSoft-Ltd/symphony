package symphony

import kommander.expect
import kotlin.test.Test

class OutputFieldsTest {

    data class Person(var name: String, var age: String)

    class TestFormFields : Fields<Person>(Person("John", "")) {
        val name = text(
            name = output::name
        )
        val age = text(
            name = output::age
        )
    }

    @Test
    fun should_easily_interact_with_text_fields() {
        val fields = TestFormFields()
        val name = fields.name
        val age = fields.age
        expect(name.output).toBe("John")
        expect(fields.output).toBe(Person("John", ""))
        name.set("Anderson")
        expect(name.output).toBe("JohnAnderson")
        name.set("Anderson")
        expect(name.output).toBe("Anderson")
        age.set("22")
        expect(age.output).toBe("22")
        expect(fields.output).toBe(Person("Anderson", "22"))
    }
}