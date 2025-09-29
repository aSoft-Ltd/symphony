package symphony

import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch
import kollections.Collection

typealias PageLoaderFunction<T> = (PageLoaderParams) -> Later<Collection<T>>