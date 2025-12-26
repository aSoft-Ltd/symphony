package symphony


typealias PageLoaderFunction<T> = suspend (PageLoaderParams) -> Collection<T>