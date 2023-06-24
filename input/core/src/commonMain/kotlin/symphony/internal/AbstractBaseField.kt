@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import kollections.iEmptyList
import neat.ValidationFactory
import neat.required
import symphony.Feedbacks
import symphony.Label
import symphony.BaseField
import symphony.BaseFieldState
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

open class AbstractBaseField<O>(
    val name: KMutableProperty0<O>,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    val onChange: Changer<O>?,
    factory: ValidationFactory<O>?
) : AbstractField<O, BaseFieldState<O>>(label, factory), BaseField<O> {

    override fun set(value: O) {
        val res = validator.validate(value)
        val output = res.value
        name.set(output)
        state.value = state.value.copy(
            output = output,
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(output)
    }

    override fun BaseFieldState<O>.with(
        hidden: Boolean,
        feedbacks: Feedbacks
    ) = copy(hidden = hidden, feedbacks = feedbacks)

    override fun cleared() = initial.copy(output = null)

    override val initial: BaseFieldState<O> = BaseFieldState(
        name = name.name,
        label = Label(label, this.validator.required),
        hidden = hidden,
        hint = hint,
        required = this.validator.required,
        output = value,
        feedbacks = Feedbacks(iEmptyList()),
    )
}