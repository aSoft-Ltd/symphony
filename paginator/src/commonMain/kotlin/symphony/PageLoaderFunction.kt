package symphony

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kollections.Collection

typealias PageLoaderFunction<T> = (PageLoaderParams) -> Later<Collection<T>>