package symphony.internal

import symphony.Feedbacks
import symphony.Label
import symphony.MultiChoiceFieldState
import symphony.Option
import symphony.SearchBy
import symphony.Visibility

data class MultiChoiceFieldStateImpl<out O>(
    override val name: String,
    override val label: Label,
    override val items: Collection<O & Any>,
    override val key: String,
    override val searchBy: SearchBy,
    override val selectedItems: List<O>,
    override val selectedOptions: List<Option>,
    override val hint: String,
    override val visibility: Visibility,
    override val required: Boolean,
    override val output: List<O>?,
    override val feedbacks: Feedbacks
) : MultiChoiceFieldState<O>