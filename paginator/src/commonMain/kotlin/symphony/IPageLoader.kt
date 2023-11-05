package symphony

import koncurrent.Later

sealed interface IPageLoader<out P : AbstractPage> {
    fun load(page: Int, capacity: Int): Later<P>
}