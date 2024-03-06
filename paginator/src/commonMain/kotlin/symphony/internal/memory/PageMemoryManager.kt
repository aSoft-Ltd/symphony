package symphony.internal.memory

import symphony.AbstractPage
import symphony.PageFindResult
import symphony.PageLoaderParams

internal abstract class PageMemoryManager<out T, out P : AbstractPage, R : PageFindResult<T>>(
    internal val entries: MutableMap<PageLoaderParams,@UnsafeVariance P>
) {
    fun save(params: PageLoaderParams,page: @UnsafeVariance P): P {
        entries[params] = page
        return page
    }

    fun load(params: PageLoaderParams): P? = entries[params]

    fun clear() = entries.clear()

    abstract fun load(row: Int, params: PageLoaderParams): R?

    abstract fun load(item: @UnsafeVariance T): R?
}