package symphony.internal

import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.SearchBy
import symphony.SingleChoiceFieldState
import symphony.Visibility

data class SingleChoiceFieldStateImpl<out O>(
    override val name: String,
    override val label: Label,
    override val items: List<O & Any>,
    override val key: String,
    override val searchBy: SearchBy,
    override val selectedItem: O?,
    override val selectedOption: Option?,
    override val hint: String,
    override val visibility: Visibility,
    override val required: Boolean,
    override val output: O?,
    override val feedbacks: Feedbacks
) : SingleChoiceFieldState<O>