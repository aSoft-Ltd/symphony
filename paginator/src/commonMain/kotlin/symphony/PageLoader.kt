package symphony

import koncurrent.Later

typealias PageLoader<T> = (no: Int, capacity: Int) -> Later<Collection<T>>