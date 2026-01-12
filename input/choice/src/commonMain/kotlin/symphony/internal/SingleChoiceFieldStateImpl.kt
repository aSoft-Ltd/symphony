package symphony.internal

import symphony.Feedbacks
import symphony.Label
import symphony.SearchBy
import symphony.SingleChoiceFieldState
import symphony.SingleSelectedChoice
import symphony.Visibility

internal data class SingleChoiceFieldStateImpl<out O>(
    override val name: String,
    override val label: Label,
    internal val options: Collection<O & Any>,
    override val items: Collection<O & Any>,
    override val key: String,
    override val searchBy: SearchBy,
    override val selected: SingleSelectedChoice<O>?,
    override val hint: String,
    override val visibility: Visibility,
    override val required: Boolean,
    override val feedbacks: Feedbacks
) : SingleChoiceFieldState<O> {
    override val output by lazy { selected?.item }
}