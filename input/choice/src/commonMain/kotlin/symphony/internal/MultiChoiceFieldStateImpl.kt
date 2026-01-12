package symphony.internal

import symphony.Feedbacks
import symphony.Label
import symphony.MultiChoiceFieldState
import symphony.MultiSelectedChoice
import symphony.SearchBy
import symphony.Visibility

internal data class MultiChoiceFieldStateImpl<out O>(
    override val name: String,
    override val label: Label,
    internal val options: Collection<O & Any>,
    override val items: Collection<O & Any>,
    override val key: String,
    override val searchBy: SearchBy,
    override val selected: MultiSelectedChoice<O>,
    override val hint: String,
    override val visibility: Visibility,
    override val required: Boolean,
    override val feedbacks: Feedbacks
) : MultiChoiceFieldState<O> {
    override val output: List<O> by lazy { selected.items }
}