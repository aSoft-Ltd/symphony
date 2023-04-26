package symphony.forms

import kommander.expect
import kommander.toBe
import kase.Failure
import kase.Submitting
import kase.Success
import kase.Validating
import kollections.List
import kollections.iListOf
import kollections.toIList
import koncurrent.Later
import kotlinx.serialization.Serializable
import cinematic.expect
import cinematic.toHaveGoneThrough2
import cinematic.toHaveGoneThrough3
import symphony.Option
import symphony.InputFieldState
import symphony.Fields
import symphony.Form
import symphony.FormActionsBuildingBlock
import symphony.name
import symphony.selectMany
import kotlin.test.Test
import symphony.FormConfig

class FormWithMultiSelectDropDownTest {
    @Serializable
    enum class Color {
        Red, Green, Blue
    }

    @Serializable
    data class Param(
        val name: String,
        val color: List<Color>
    )

    class TestFields : Fields() {
        val name = name(
            name = Param::name,
            isRequired = true
        )

        val color = selectMany(
            name = Param::color,
            items = Color.values().toIList(),
            mapper = { Option(it.name) },
            isRequired = true
        )
    }

    class TestForm<F : Fields>(
        override val fields: F,
        builder: FormActionsBuildingBlock<Param, Any?>
    ) : Form<F, Param, Any?>(
        heading = "Test Form",
        details = "This is a form for testing",
        fields = fields,
        config = FormConfig(exitOnSubmitted = false),
        builder
    )

    @Test
    fun should_fail_to_submit_when_a_required_nothing_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.clear()
        }

        form.submit()
        val (_, f) = expect(form.ui).toHaveGoneThrough2<Validating, Failure<Any?>>()
        expect(f.message).toBe("You have 1 invalid input")
        val err = expect(form.fields.color.feedback.value).toBe<InputFieldState.Error>()
        expect(err.message).toBe("Color is required")
    }

    @Test
    fun should_submit_when_a_required_multi_select_has_been_selected() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.addSelectedItem(Color.Blue)
        }
        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<Any?>>()
    }

    @Test
    fun should_submit_when_a_required_select_multi_has_been_selected_with_multiple_inputs() {
        val form = TestForm(TestFields()) {
            onSubmit { Later(0) }
        }

        form.fields.apply {
            name.type("John")
            color.addSelectedItem(Color.Blue)
            color.addSelectedItem(Color.Red)
        }

        form.submit()
        expect(form.ui).toHaveGoneThrough3<Validating, Submitting, Success<Any?>>()
        expect(form.fields.color.data.value.output).toBe(iListOf(Color.Blue, Color.Red))
    }
}