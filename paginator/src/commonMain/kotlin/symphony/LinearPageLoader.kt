package symphony

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch

interface LinearPageLoader<out T> : PageLoader<LinearPage<T>> {
    override fun load(page: Int, capacity: Int): Later<LinearPage<T>>
}