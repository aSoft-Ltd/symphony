package symphony

import kollections.iListOf
import kommander.expect
import kotlin.test.Test

class SingleChoiceTest {
    var name: String? = null

    @Test
    fun should_be_able_to_search_through_by_filtering() {
        val options = iListOf("Jane", "John", "June", "Jean", "Peter")
        val select = SingleChoiceField(
            name = ::name,
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        select.searchByFiltering("P")
        expect(select.state.value.items).toBeOfSize(1)
    }

    @Test
    fun should_be_able_to_search_through_like_terms_by_filtering() {
        val options = iListOf("Jane", "John", "June", "Jean", "Peter")
        val select = SingleChoiceField(
            name = ::name,
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        select.searchByFiltering("J")
        expect(select.state.value.items).toBeOfSize(4)
    }

    @Test
    fun should_be_able_to_search_through_like_terms_by_ordering() {
        val options = iListOf("Jane", "John", "June", "Jean", "Peter")
        val select = SingleChoiceField(
            name = ::name,
            items = options,
            mapper = { Option(it) },
            filter = { item, key -> item.contains(key) }
        )

        select.searchByOrdering("P")
        expect(select.state.value.items).toBeOfSize(5)
        expect(select.state.value.items.first()).toBe("Peter")
    }
}