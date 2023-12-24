package symphony

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch

sealed interface PageLoader<out P : AbstractPage> {
    fun load(page: Int, capacity: Int): Later<P>
}