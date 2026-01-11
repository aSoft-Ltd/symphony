package symphony

import kommander.expect
import kotlin.test.Test

class SingleChoiceOptionReplaceTest {
    var name: String? = null

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
        val select = SingleChoiceField(
            name = "",
            items = drinks[0].brands,
            mapper = { Option(it) },
        )

        val watcher = select.state.watchEagerly {
            println("Items: ${it.items}")
        }
        expect(select.items).toContain("Coke", "Pepsi")

        select.replaceItems(drinks[1].brands)

        expect(select.items).toContain("Orange", "Apple")
        watcher.stop()
    }
}