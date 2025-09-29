package symphony

import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch

sealed interface PageLoader<out P : Page> {
    fun load(params: PageLoaderParams): Later<P>
}