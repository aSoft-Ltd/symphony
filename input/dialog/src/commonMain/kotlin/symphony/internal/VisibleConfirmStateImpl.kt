package symphony.internal

import kase.ExecutorState
import symphony.VisibleConfirmState

@PublishedApi
internal data class VisibleConfirmStateImpl<out S>(
    override val heading: String,
    override val details: String,
    override val message: String,
    override val subject: S,
    override val phase: ExecutorState<Unit>
) : VisibleConfirmState<S>