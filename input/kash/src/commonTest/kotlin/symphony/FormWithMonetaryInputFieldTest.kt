package symphony

import kommander.expect
import kash.Currency
import kash.Monetary
import koncurrent.Later
import kotlinx.serialization.Serializable
import kotlin.test.Test

class FormWithMonetaryInputFieldTest {
    @Serializable
    data class TestParams(val name: String, val price: Monetary)

    class TestFields(currency: Currency?) : Fields() {
        val name = name()
        val price = monetary(
            name = TestParams::price
        )
    }

    class TestForm(
        curreny: Currency?,
        init: FormInitializer<TestParams, TestParams>
    ) : Form<TestFields, TestParams, TestParams>(
        heading = "Test Form",
        details = "This is a test form",
        fields = TestFields(curreny),
        config = FormConfig(),
        initializer = init
    )

    @Test
    fun should_submit_from_a_form_with_a_monetary_value() {
        var params: TestParams? = null
        val form = TestForm(Currency.TZS) {
            onCancel { println("Cancelled") }
            onSubmit {
                params = it
                Later(it)
            }
        }

        with(form.fields) {
            name.type("Lamax")
            price.setAmount(200)
        }
        form.submit()

        expect(params?.price?.amountAsDouble).toBe(200.0)
    }
}