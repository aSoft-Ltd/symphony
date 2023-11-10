package symphony

import koncurrent.Later

typealias PageLoaderFunction<T> = (no: Int, capacity: Int) -> Later<Collection<T>>