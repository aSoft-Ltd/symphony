package symphony.internal

import cinematic.mutableLiveOf
import symphony.HiddenPeekabooState
import symphony.Peekaboo
import symphony.PeekabooState
import symphony.VisiblePeekabooState

@PublishedApi
internal class PeekabooImpl<P, T>(private val factory: (Peekaboo<P, *>.(P) -> T)?) : Peekaboo<P, T> {

    override val state by lazy { mutableLiveOf<PeekabooState<T>>(HiddenPeekabooState) }

    override fun hide() {
        state.value = HiddenPeekabooState
    }

    override fun show(params: P) {
        val s = factory?.invoke(this, params)
        state.value = if (s == null) HiddenPeekabooState else VisiblePeekabooState(s)
    }
}