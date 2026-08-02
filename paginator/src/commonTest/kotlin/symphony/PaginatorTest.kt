package symphony

import kase.Failure
import kase.Loading
import kase.Pending
import kase.Success
import kommander.expect
import kommander.toBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class PaginatorTest {
    @Test
    fun single_page_paginator_should_always_return_the_same_list() = runTest {
        val people = listOf(1, 2, 3, 4, 5).map { Person("Andy $it", age = 12 + it) }
        val p = linearPaginatorOf<Person>(5)
        p.initialize { people.paged(it) }
        p.refreshAllPages()
        expect(p.currentPageOrNull?.capacity).toBe(5)
    }

    @Test
    @Ignore
    fun paginator_should_be_able_to_paginate_through_different_pages() = runTest {
        val p = linearPaginatorOf<Person>(10)
        p.initialize { Person.List.paged(it) }
        val watcher = p.current.watchEagerly {
            println("Page at: ${it.data?.number}")
        }
        p.refreshAllPages()
        expect(p.current.value).toBe<Success<Any?>>()
        expect(p.currentPageOrNull?.number).toBe(1)
        expect(p.currentPageOrNull?.items?.size).toBe(10)
        expect(p.currentPageOrNull?.capacity).toBe(10)

        p.loadNextPage()
        expect(p.currentPageOrNull?.number).toBe(2)
        expect(p.currentPageOrNull?.items?.size).toBe(10)
        expect(p.currentPageOrNull?.capacity).toBe(10)

        p.loadPreviousPage()
        expect(p.currentPageOrNull?.number).toBe(1)
        expect(p.currentPageOrNull?.items?.size).toBe(10)
        expect(p.currentPageOrNull?.capacity).toBe(10)

        p.loadPage(2)
        expect(p.currentPageOrNull?.number).toBe(2)
        expect(p.currentPageOrNull?.items?.size).toBe(10)
        expect(p.currentPageOrNull?.capacity).toBe(10)

        p.loadNextPage()
        expect(p.currentPageOrNull?.number).toBe(3)
        expect(p.currentPageOrNull?.items?.size).toBe(5)
        expect(p.currentPageOrNull?.capacity).toBe(10)

        p.loadFirstPage()
        expect(p.currentPageOrNull?.number).toBe(1)
        expect(p.currentPageOrNull?.items?.size).toBe(10)
        expect(p.currentPageOrNull?.capacity).toBe(10)

        p.loadLastPage()
        watcher.stop()
        expect(p.currentPageOrNull?.number).toBe(-1)
        expect(p.currentPageOrNull?.items?.size).toBe(5)
        expect(p.currentPageOrNull?.capacity).toBe(10)
    }

    @Test
    fun should_be_able_to_deinitialize_when_initilaized_with_setup_method() = runTest {
        val p = linearPaginatorOf<Person>(10)
        p.setup { update(Person.List.paged(it)) }
        p.finalize()
    }

    @Test
    fun can_setup_paginator_again_after_being_finalized() = runTest {
        val p = linearPaginatorOf<Person>(10)
        p.setup { update(Person.List.paged(it)) }
        p.finalize()

        p.setup { params ->
            setPageCapacity(20)
            update(Person.List.paged(params))
        }
        p.finalize()
    }

    @Test
    fun should_be_able_to_set_page_count() = runTest {
        val persons = Persons(9,2.seconds)
        val p = linearPaginatorOf<Person>(10)
        p.current.watchLazily { page ->
            when (page) {
                is Failure<*> -> println("Error")
                is Loading<*> -> println("Loading ${page.data}")
                is Pending -> println("Pending")
                is Success<*> -> page.data?.items?.forEach { println(it.item) }
            }
            println("= ".repeat(20))
        }
        p.setup {
            val paged = persons.all(CursorPaginationParams(ForwardCursor.First, 4))
            update(paged.content)
        }
        p.loadNextPage()
        p.finalize()
    }
}