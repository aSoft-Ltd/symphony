@file:JsExport

package symphony.internal

import kotlinx.JsExport
import symphony.Feedbacks
import symphony.ListFieldState
import symphony.Visibility

data class LIstFieldImplState<O>(
    override val visibility: Visibility,
    override val required: Boolean,
    override val output: MutableList<O>,
    override val feedbacks: Feedbacks
) : ListFieldState<O>