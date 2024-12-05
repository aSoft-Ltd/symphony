package symphony

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kollections.Collection

typealias PageLoaderFunction<T> = (PageLoaderParams, PageLoaderSource) -> Later<Collection<T>>
typealias PageCacheFunction<T> = (Collection<T>) -> Later<Unit>

typealias GroupedPageLoaderFunction<G, T> = (PageLoaderParams, PageLoaderSource) -> Later<Collection<Chunk<G, T>>>

typealias LoaderFunction<T> = (PageLoaderParams) -> Later<Collection<T>>