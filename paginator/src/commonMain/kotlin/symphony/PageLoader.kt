package symphony

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch

sealed interface PageLoader<out P : Page> {
    fun load(params: PageLoaderParams): Later<P>
}