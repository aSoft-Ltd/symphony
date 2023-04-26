package symphony.fields

import kommander.expect
import koncurrent.Later
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import symphony.Fields
import symphony.Form
import symphony.FormActionsBuildingBlock
import symphony.textTo
import kotlin.test.Test
import symphony.FormConfig

class TextMappedInputFieldTest {
    @Serializable
    data class Category(val uid: String = "unset", val name: String)

    class TestFields : Fields() {
        val category = textTo(name = "Category") {
            Category(name = it ?: "Nothing")
        }
    }

    class TestForm(initializer: FormActionsBuildingBlock<JsonObject, Any?>) : Form<TestFields, JsonObject, Any?>(
        heading = "Test Form",
        details = "A Form for testing TextMappedInputField",
        fields = TestFields(),
        config = FormConfig(),
        initializer = initializer
    )

    @Test
    fun should_test_people() = runTest {
        val form = TestForm {
            onSubmit {
                println("Submitted with : ${it.toMap()}")
                Later(Unit)
            }
        }
        form.fields.category.set("Test")
        expect(form.fields.category.data.value.output).toBe(Category(name = "Test"))
        form.submit().await()
    }
}