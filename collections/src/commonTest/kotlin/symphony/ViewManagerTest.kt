package symphony

import kommander.expect
import kotlin.test.Test
class ViewManagerTest {
    enum class TestView {
        View1,
        View2
    }
    @Test
    fun should_construct_a_view_manager_from_an_enum() {
        val views = viewsOf<TestView>()
        views.select(TestView.View1)
        expect(views.current.value).toBe(TestView.View1)
    }

    @Test
    fun should_be_able_to_select_a_specific_view() {
        val views = viewsOf<TestView>()
        views.select(TestView.View2)
        expect(views.current.value).toBe(TestView.View2)
    }

    @Test
    fun should_be_able_to_use_different_kind_of_views() {
        val views = viewsOf("Table","List","Grid")
        views.select("Grid")
        expect(views.current.value).toBe("Grid")
    }

    @Test
    fun can_invoke_the_callback_when_selected() {
        var selected: String? = null
        val views = viewsOf("A","B","C") {
            selected = it
        }
        views.select("C")
        expect(selected).toBe("C")
        expect(views.current.value).toBe("C")
    }
}