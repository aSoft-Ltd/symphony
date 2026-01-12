package symphony

import kommander.expect
import kotlin.test.Test

class MultiChoiceOptionReplaceTest {
    var output = mutableListOf<String>()

    class Drink(
        val name: String,
        val brands: List<String>
    )

    val drinks = listOf(
        Drink(
            name = "Soda",
            brands = listOf("Coke", "Pepsi")
        ),
        Drink(
            name = "Juice",
            brands = listOf("Orange", "Apple")
        )
    )

    @Test
    fun should_be_able_to_change_items() {
        val field = MultiChoiceField(
            name = ::output,
            items = drinks[0].brands,
            mapper = { Option(it) },
        )

        val watcher = field.state.watchEagerly {
            println("Items: ${it.items}")
        }
        expect(field.items).toContain("Coke", "Pepsi")

        field.replaceItems(drinks[1].brands)

        expect(field.items).toContain("Orange", "Apple")
        watcher.stop()
    }

    @Test
    fun should_update_the_state_when_items_are_replaced() {
        val field = MultiChoiceField(
            name = ::output,
            items = drinks[0].brands,
            mapper = { Option(it) },
        )
        expect(field.items).toContain("Coke", "Pepsi")
        expect(field.state.value.items).toContain("Coke", "Pepsi")

        field.replaceItems(drinks[1].brands)

        expect(field.items).toContain("Orange", "Apple")
        expect(field.state.value.items).toContain("Orange", "Apple")
    }
}