package symphony

import kommander.expect
import kommander.toBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

class Paginator2Test {
    val duration = 2.milliseconds

    @Test
    fun general_paginator_should_always_return_the_same_list() = runTest {
        val persons = Persons(total = 9, duration)
        val p = paginator<Person>(5)
        p.initialize { update(persons.all(it)) }
        expect(p.params.value.capacity).toBe(5)
        expect(p.state.value.items.capacity).toBe(5)
        expect(p.state.value.items.content).toHave(5)
        expect(p.state.value.items.total).toBe(9)
        expect(p.state.value.page).toBe(1)
        p.finalize()
    }

    @Test
    fun general_paginator_should_be_able_to_load_next_page_with_cursor_based_paginator() = runTest {
        val persons = Persons(total = 14, duration)
        val p = paginator<Person>(5)
        val people = mutableListOf<Person>()
        p.initialize {
            people.clear()
            val result = persons.all(it)
            update(result)
            people.addAll(result.items.content)
        }

        val s1 = expect(p.state.value).toBe<CursorPaged<*>>()
        expect(s1.cursor.next).toBe(people.last().uid)
        expect(s1.cursor.next).toBeNonNull()
        expect(s1.cursor.prev).toBeNull()
        expect(s1.items.content).toHave(5)
        expect(s1.items.capacity).toBe(5)
        expect(s1.page).toBe(1)
        expect(s1.items.total).toBe(14)

        p.loadNextPage()

        val s2 = expect(p.state.value).toBe<CursorPaged<*>>()
        expect(s2.cursor.next).toBe(people.last().uid)
        expect(s2.cursor.next).toBeNonNull()
        expect(s2.cursor.prev).toBeNonNull()
        expect(s2.cursor.prev).toBe(people.first().uid)
        expect(s2.items.capacity).toBe(5)
        expect(s2.items.content).toHave(5)
        expect(s2.page).toBe(2)
        expect(s2.items.total).toBe(14)

        p.loadNextPage()

        val s3 = expect(p.state.value).toBe<CursorPaged<*>>()
        expect(s3.cursor.next).toBeNull()
        expect(s3.cursor.prev).toBeNonNull()
        expect(s3.items.capacity).toBe(5)
        expect(s3.items.content).toHave(4)
        expect(s3.page).toBe(3)
        expect(s3.items.total).toBe(14)
        p.finalize()
    }

    @Test
    fun general_paginator_should_be_able_to_load_previous_page_with_cursor_based_paginator() = runTest {
        val persons = Persons(total = 14, duration)
        val p = paginator<Person>(5)
        val people = mutableListOf<Person>()
        p.initialize {
            people.clear()
            val result = persons.all(it)
            update(result)
            people.addAll(result.items.content)
        }
        p.loadNextPage()
        p.loadNextPage()

        p.loadPreviousPage()
        val s1 = expect(p.state.value).toBe<CursorPaged<*>>()
        expect(s1.cursor.next).toBeNonNull()
        expect(s1.cursor.prev).toBeNonNull()
        expect(s1.items.capacity).toBe(5)
        expect(s1.items.content).toHave(5)
        expect(s1.page).toBe(2)
        expect(s1.items.total).toBe(14)

        p.loadPreviousPage()

        val s2 = expect(p.state.value).toBe<CursorPaged<*>>()
        expect(s2.cursor.next).toBe(people.last().uid)
        expect(s2.cursor.next).toBeNonNull()
        expect(s2.cursor.prev).toBeNull()
        expect(s2.items.content).toHave(5)
        expect(s2.items.capacity).toBe(5)
        expect(s2.items.total).toBe(14)
        expect(s2.page).toBe(1)
        expect(s2.items.total).toBe(14)
        p.finalize()
    }

    @Test
    fun general_paginator_should_be_able_to_a_fixed_page_with_offset_based_paginator() = runTest {
        val persons = Persons(total = 14, duration)
        val p = paginator<Person>(5)
        val people = mutableListOf<Person>()
        p.initialize {
            people.clear()
            val result = persons.all(it)
            update(result)
            people.addAll(result.items.content)
        }

        p.load(page = 2)
        val s1 = expect(p.state.value).toBe<OffsetPaged<*>>()
        expect(s1.items.capacity).toBe(5)
        expect(s1.items.content).toHave(5)
        expect(s1.page).toBe(2)
        expect(s1.items.total).toBe(14)

        p.load(page = 3)
        val s2 = expect(p.state.value).toBe<OffsetPaged<*>>()
        expect(s2.items.capacity).toBe(5)
        expect(s2.items.content).toHave(4)
        expect(s2.page).toBe(3)
        expect(s2.items.total).toBe(14)
    }
}