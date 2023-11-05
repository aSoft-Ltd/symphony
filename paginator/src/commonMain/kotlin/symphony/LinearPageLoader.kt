package symphony

import koncurrent.Later

interface LinearPageLoader<out T> : IPageLoader<LinearPage<T>> {
    override fun load(page: Int, capacity: Int): Later<LinearPage<T>>
}