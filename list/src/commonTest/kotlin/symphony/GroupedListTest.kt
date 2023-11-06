package symphony

import kotlin.test.Test

class GroupedListTest {

    @Test
    fun should_be_able_to_group_by_age() {
        val paginator = linearPaginatorOf(Person.List, capacity = 15)
        val selector = selectorOf(paginator)
        val list = lazyListOf(paginator, selector) {
            if (it.age < 15) it to "Below 15" else it to "Above 15"
        }
        paginator.loadFirstPage()
        println(list)
    }

    @Test
    fun should_be_able_to_group_by_gender() {
        val paginator = linearPaginatorOf(Person.List, capacity = 15)
        val selector = selectorOf(paginator)
        val list = lazyListOf(paginator, selector) {
            if (it.age < 15) it to it.gender else it to it.gender
        }

        list.paginator.loadFirstPage()
        println(list)
    }

    sealed interface PeopleGroupedBy {
        data object Age : PeopleGroupedBy
        data object Hobby : PeopleGroupedBy
        data object Gender : PeopleGroupedBy
    }

    sealed interface Group

    sealed interface AgeGroup : Group

    data object BelowAgeGroup : AgeGroup
    data object AboveAgeGroup : AgeGroup

    data class HobbyGroup(val hobby: Person.Hobby) : Group
    data class GenderGroup(val gender: Person.Gender) : Group

    @Test
    fun should_be_able_to_dynamically_change_the_grouping_after_list_instantiating() {
        val paginator = linearPaginatorOf(Person.List, capacity = 15)
        val selector = selectorOf(paginator)
        var groupedBy: PeopleGroupedBy = PeopleGroupedBy.Age
        val list = lazyListOf(paginator, selector) {
            when (groupedBy) {
                PeopleGroupedBy.Age -> if (it.age <= 15) it to BelowAgeGroup else it to AboveAgeGroup
                PeopleGroupedBy.Hobby -> it to HobbyGroup(it.hobby)
                PeopleGroupedBy.Gender -> it to GenderGroup(it.gender)
            }
        }
        list.paginator.loadFirstPage()
        println(list)

        groupedBy = PeopleGroupedBy.Gender
        list.paginator.loadFirstPage()
        println(list)

        groupedBy = PeopleGroupedBy.Hobby
        list.paginator.loadFirstPage()
        println(list)
    }
}