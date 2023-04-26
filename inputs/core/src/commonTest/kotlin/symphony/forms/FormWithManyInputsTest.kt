package symphony.forms

import cinematic.expect
import cinematic.toHaveGoneThrough3
import kase.Submitting
import kase.Success
import kase.Validating
import kollections.List
import kollections.iListOf
import kollections.toIList
import kommander.expect
import koncurrent.Later
import kotlin.test.Test
import kotlinx.serialization.Serializable
import symphony.Fields
import symphony.Form
import symphony.FormConfig
import symphony.Option
import symphony.name
import symphony.selectMany
import symphony.selectSingle
import symphony.text

class FormWithManyInputsTest {

    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    class AllFields : Fields() {
        val name = name(isRequired = true)
        val username = text(name = "username", isRequired = true)
        val address = text(name = "address", isRequired = true)
        val color = selectSingle(
            name = "color",
            items = Color.values().toIList(),
            mapper = { Option(it.name) },
            isRequired = true
        )
        val colors = selectMany(
            name = "colors",
            items = Color.values().toIList(),
            mapper = { Option(it.name) },
            isRequired = true
        )
    }

    @Serializable
    class AllParams(
        val name: String,
        val username: String,
        val address: String,
        val color: Color,
        val colors: List<Color>
    )

    @Test
    fun should_be_able_to_submit_fields() {
        var params: AllParams? = null
        val form = Form<AllFields, AllParams, Any?>(
            heading = "The god form",
            details = "A form to test things out",
            fields = AllFields(),
            config = FormConfig(exitOnSubmitted = false)
        ) {
            onSubmit {
                params = it
                Later(it)
            }
        }
        form.fields.apply {
            name.type("Andy")
            username.type("andy@lamax.com")
            address.type("0752748674")
            color.selectItem(Color.Red)
            colors.addSelectedItem(Color.Green)
            colors.addSelectedItem(Color.Blue)
        }
        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<Any>>()
        expect(params?.name).toBe("Andy")
        expect(params?.username).toBe("andy@lamax.com")
        expect(params?.address).toBe("0752748674")
        expect(params?.color).toBe(Color.Red)
        expect(params?.colors).toBe(iListOf(Color.Green, Color.Blue))
    }
}