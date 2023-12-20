package symphony

import kollections.first
import kollections.listOf
import kollections.size
import kommander.expect
import kotlin.test.Test

class SingleChoiceTest {
    var name: String? = null

    @Test
    fun should_be_able_to_search_through_by_filtering() {
        val options = listOf("Jane", "John", "June", "Jean", "Peter")
        val select = SingleChoiceField(
            name = "",
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        select.setSearchKey("P")
        select.setSearchByFiltering()
        select.search()
        expect(select.state.value.items.size).toBe(1)
    }

    @Test
    fun should_be_able_to_search_through_like_terms_by_filtering() {
        val options = listOf("Jane", "John", "June", "Jean", "Peter")
        val select = SingleChoiceField(
            name = "test-field",
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        select.setSearchKey("J")
        select.setSearchByFiltering()
        select.search()
        expect(select.state.value.items.size).toBe(4)
    }

    @Test
    fun should_be_able_to_search_through_like_terms_by_ordering() {
        val options = listOf("Jane", "John", "June", "Jean", "Peter")
        val select = SingleChoiceField(
            name = "test-field",
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        select.setSearchKey("P")
        select.setSearchByOrdering()
        select.search()
        expect(select.state.value.items.size).toBe(5)
        expect(select.state.value.items.first()).toBe("Peter")
    }
}