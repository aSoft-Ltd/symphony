package symphony

import kommander.expect
import kommander.toBe
import kotlinx.coroutines.test.runTest
import symphony.internal.GeneralPaginatorImpl
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class Paginator2Test {

    @Test
    fun general_paginator_should_always_return_the_same_list() = runTest {
        val persons = Persons(total = 9, 2.seconds)
        val p = GeneralPaginatorImpl<Person>(5)
        p.initialize { update(persons.all(it)) }
        expect(p.state.value.capacity).toBe(5)
        expect(p.state.value.content).toHave(5)
        expect(p.state.value.number.total).toBe(9)
        expect(p.state.value.number.current).toBe(1)
        p.finalize()
    }

    @Test
    fun general_paginator_should_be_able_to_load_next_page_with_cursor_based_paginator() = runTest {
        val persons = Persons(total = 14, duration = 2.milliseconds)
        val p = GeneralPaginatorImpl<Person>(5)
        val people = mutableListOf<Person>()
        p.initialize {
            people.clear()
            val result = persons.all(it)
            update(result)
            people.addAll(result.content)
        }

        val s1 = expect(p.state.value).toBe<PagedCursorData<*>>()
        expect(s1.cursor.next).toBe(people.last().uid)
        expect(s1.cursor.next).toBeNonNull()
        expect(s1.cursor.prev).toBeNull()
        expect(s1.content).toHave(5)
        expect(s1.capacity).toBe(5)
        expect(s1.number.current).toBe(1)
        expect(s1.number.total).toBe(14)

        p.loadNextPage()

        val s2 = expect(p.state.value).toBe<PagedCursorData<*>>()
        expect(s2.cursor.next).toBe(people.last().uid)
        expect(s2.cursor.next).toBeNonNull()
        expect(s2.cursor.prev).toBeNonNull()
        expect(s2.cursor.prev).toBe(people.first().uid)
        expect(s2.capacity).toBe(5)
        expect(s2.content).toHave(5)
        expect(s2.number.current).toBe(2)
        expect(s2.number.total).toBe(14)

        p.loadNextPage()

        val s3 = expect(p.state.value).toBe<PagedCursorData<*>>()
        expect(s3.cursor.next).toBeNull()
        expect(s3.cursor.prev).toBeNonNull()
        expect(s3.capacity).toBe(5)
        expect(s3.content).toHave(4)
        expect(s3.number.current).toBe(3)
        expect(s3.number.total).toBe(14)
        p.finalize()
    }

    @Test
    fun general_paginator_should_be_able_to_load_previous_page_with_cursor_based_paginator() = runTest {
        val persons = Persons(total = 14, duration = 2.milliseconds)
        val p = GeneralPaginatorImpl<Person>(5)
        val people = mutableListOf<Person>()
        p.initialize {
            people.clear()
            val result = persons.all(it)
            update(result)
            people.addAll(result.content)
        }
        p.loadNextPage()
        p.loadNextPage()

        p.loadPreviousPage()
        val s1 = expect(p.state.value).toBe<PagedCursorData<*>>()
        expect(s1.cursor.next).toBeNonNull()
        expect(s1.cursor.prev).toBeNonNull()
        expect(s1.capacity).toBe(5)
        expect(s1.content).toHave(5)
        expect(s1.number.current).toBe(2)
        expect(s1.number.total).toBe(14)

        p.loadPreviousPage()

        val s2 = expect(p.state.value).toBe<PagedCursorData<*>>()
        expect(s2.cursor.next).toBe(people.last().uid)
        expect(s2.cursor.next).toBeNonNull()
        expect(s2.cursor.prev).toBeNull()
        expect(s2.content).toHave(5)
        expect(s2.capacity).toBe(5)
        expect(s2.number.total).toBe(14)
        expect(s2.number.current).toBe(1)
        expect(s2.number.total).toBe(14)
        p.finalize()
    }

    @Test
    fun general_paginator_should_be_able_to_a_fixed_page_with_offset_based_paginator() = runTest {
        val persons = Persons(total = 14, duration = 2.milliseconds)
        val p = GeneralPaginatorImpl<Person>(5)
        val people = mutableListOf<Person>()
        p.initialize {
            people.clear()
            val result = persons.all(it)
            update(result)
            people.addAll(result.content)
        }

        p.load(page = 2)
        val s1 = expect(p.state.value).toBe<PagedOffsetData<*>>()
        expect(s1.capacity).toBe(5)
        expect(s1.content).toHave(5)
        expect(s1.number.current).toBe(2)
        expect(s1.number.total).toBe(14)

        p.load(page = 3)
        val s2 = expect(p.state.value).toBe<PagedOffsetData<*>>()
        expect(s2.capacity).toBe(5)
        expect(s2.content).toHave(4)
        expect(s2.number.current).toBe(3)
        expect(s2.number.total).toBe(14)
    }
}