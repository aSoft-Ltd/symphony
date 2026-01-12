package symphony

import kommander.expect
import kotlin.test.Test

class MultiChoiceSelectionTest {
    var names: MutableList<String> = mutableListOf()

    @Test
    fun selection_should_reflect_in_the_state_object() {
        val options = listOf("Jane", "John", "June", "Jean", "Peter")
        val field = MultiChoiceField(
            name = ::names,
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        field.addSelectedItem("Jane")

        expect(field.state.value.selected.items).toContain("Jane")
        expect(field.selected.items).toContain("Jane")
    }

    @Test
    fun should_have_the_same_number_of_items_after_being_cleared() {
        val options = listOf("Jane", "John", "June", "Jean", "Peter")
        val field = MultiChoiceField(
            name = ::names,
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        field.setSearchKey("P")
        field.setSearchByFiltering()
        field.search()
        expect(field.state.value.items.size).toBe(1)
        expect(field.state.value.items.first()).toBe("Peter")

        field.clearSearchKey()
        expect(field.state.value.items.size).toBe(5)
        expect(field.state.value.items.first()).toBe("Jane")
    }
}