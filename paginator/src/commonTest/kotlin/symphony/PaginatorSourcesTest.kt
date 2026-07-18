package symphony

import kommander.expect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class PaginatorSourcesTest {
    fun people(params: PageLoaderParams) = flow {
        delay(2.seconds)
        emit(listOf(1, 2, 3, 4, 5).map { Person("Andy $it", age = (10 * params.page) + it) })
        delay(2.seconds)
        emit(listOf(1, 2, 3, 4, 5).map { Person("Andy ${10 + it}", age = (10 * params.page) + it) })
        delay(2.seconds)
    }

    @Test
    fun should_be_able_to_load_data_from_different_sources() = runTest {
        val p = linearPaginatorOf<Person>(5)
        p.current.watchLazily {
            println(p.current.value.data?.items)
        }
        withContext(Dispatchers.Default) {
            launch {
                p.setup { params -> people(params).collect { update(it) } }
            }
            launch {
                delay(3.seconds)
                println("Loading page 2")
                p.loadPage(1)
            }
        }
        expect(p.currentPageOrNull?.capacity).toBe(5)
    }
}