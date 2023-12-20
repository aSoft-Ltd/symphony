package symphony

import kommander.expect
import kommander.toBe
import kollections.listOf
import kollections.map
import kollections.size
import kase.Pending
import kase.Success
import kotlin.test.Test

class PaginatorTest {
    @Test
    fun single_page_paginator_should_always_return_the_same_list() {
        val people = listOf(1,2,3,4,5).map { Person("Andy $it", age = 12 + it) }
        val p = linearPaginatorOf(people)
        expect(p.currentPageOrNull).toBe(null)
        p.refreshAllPages()
        expect(p.currentPageOrNull?.capacity).toBe(5)
    }

    @Test
    fun paginator_should_be_able_to_paginate_through_different_pages() {
        val p = linearPaginatorOf(Person.List)
        val watcher = p.current.watchEagerly {
            println("Page at: ${it.data?.number}")
        }
        expect(p.currentPageOrNull).toBe(null)
        expect(p.current.value).toBe(Pending)
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