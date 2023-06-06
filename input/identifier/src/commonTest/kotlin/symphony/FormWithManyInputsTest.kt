package symphony

import kommander.expect
import kase.Submitting
import kase.Success
import kase.Validating
import kollections.iListOf
import kollections.toIList
import koncurrent.Later
import kotlinx.serialization.Serializable
import cinematic.expect
import cinematic.toHaveGoneThrough3
import geo.Country
import kotlin.test.Test

class FormWithManyInputsTest {

    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    class AllFields : Fields() {
        val name = name(isRequired = true)
        val email = email(name = "email", isRequired = true)
        val phone = phone(name = "phone", isRequired = true)
        val color = selectSingle(name = "color", items = Color.values().toIList(), mapper = { Option(it.name) }, isRequired = true)
        val colors = selectMany(name = "colors", items = Color.values().toIList(), mapper = { Option(it.name) }, isRequired = true)
    }

    @Serializable
    class AllParams(
        val name: String,
        val email: String,
        val phone: String,
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
            config = FormConfig(exitOnSubmitted = false),
        ) {
            onSubmit {
                params = it
                Later(it)
            }
        }
        form.fields.apply {
            name.set("Andy")
            email.set("andy@lamax.com")
            phone.code.selectItem(Country.ZA)
            phone.number.set("0752748674")
            color.selectItem(Color.Red)
            colors.addSelectedItem(Color.Green)
            colors.addSelectedItem(Color.Blue)
        }
        expect(form.fields.phone.code.output).toBe(Country.ZA)
        expect(form.fields.phone.number.output?.toString()).toBe("752748674")
        expect(form.fields.phone.output?.toString()).toBe("27752748674")
        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<Any?>>()
        expect(params?.name).toBe("Andy")
        expect(params?.email).toBe("andy@lamax.com")
        expect(params?.phone).toBe("27752748674")
        expect(params?.color).toBe(Color.Red)
        expect(params?.colors?.toIList()).toBe(iListOf(Color.Green, Color.Blue))
    }
}