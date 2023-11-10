package symphony

import koncurrent.Later

sealed interface PageLoader<out P : AbstractPage> {
    fun load(page: Int, capacity: Int): Later<P>
}