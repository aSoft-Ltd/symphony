package symphony

import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch

interface LinearPageLoader<out T> : PageLoader<LinearPage<T>> {
    override fun load(params: PageLoaderParams): Later<LinearPage<T>>
}