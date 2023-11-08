@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import kotlin.js.JsExport
import symphony.BaseFieldState
import symphony.Label
import symphony.Visibility
import symphony.Feedbacks

data class BaseFieldImplState<out O>(
    override val name: String,
    override val label: Label,
    override val hint: String,
    override val visibility: Visibility,
    override val required: Boolean,
    override val output: O?,
    override val feedbacks: Feedbacks
) : BaseFieldState<O>