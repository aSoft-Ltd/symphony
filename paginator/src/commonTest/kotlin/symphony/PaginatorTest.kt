package symphony

import kase.Success
import kommander.expect
import kommander.toBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

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
}