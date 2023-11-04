package symphony

import kotlin.test.Test

class GroupedListTest {

    @Test
    fun should_be_able_to_group_by_age() {
        val paginator = CollectionPaginator(Person.List, capacity = 15)
        val selector = SelectionManager(paginator)
        val list = groupedListOf(paginator, selector) {
            if (it.age < 15) it to "Below 15" else it to "Above 15"
        }
        list.paginator.loadFirstPage()
        list.groups.forEach { chunk ->
            println(chunk.group)
            chunk.rows.forEach {
                println("\t${it.item}")
            }
        }
    }

    @Test
    fun should_be_able_to_group_by_gender() {
        val paginator = CollectionPaginator(Person.List, capacity = 15)
        val selector = SelectionManager(paginator)
        val list = groupedListOf(paginator, selector) {
            if (it.age < 15) it to it.gender else it to it.gender
        }

        list.paginator.loadFirstPage()
        list.groups.forEach { chunk ->
            println(chunk.group)
            chunk.rows.forEach {
                println("\t${it.item}")
            }
        }
    }
}