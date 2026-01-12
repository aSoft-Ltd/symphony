package symphony

import kommander.expect
import kotlin.test.Test

class SingleChoiceSelectionTest {
    var name: String? = null

    @Test
    fun selection_should_reflect_in_the_state_object() {
        val options = listOf("Jane", "John", "June", "Jean", "Peter")
        val field = SingleChoiceField(
            name = "",
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        field.select("Jane")

        expect(field.state.value.selected?.item).toBe("Jane")
        expect(field.selected?.item).toBe("Jane")
    }
}