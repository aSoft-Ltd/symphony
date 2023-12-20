package symphony

import koncurrent.Later
import kollections.Collection

typealias PageLoaderFunction<T> = (no: Int, capacity: Int) -> Later<Collection<T>>