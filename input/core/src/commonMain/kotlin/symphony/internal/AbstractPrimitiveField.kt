@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import kollections.iEmptyList
import neat.ValidationFactory
import neat.required
import symphony.Feedbacks
import symphony.Label
import symphony.PrimitiveField
import symphony.PrimitiveFieldState
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

open class AbstractPrimitiveField<O>(
    val name: KMutableProperty0<O>,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    factory: ValidationFactory<O>?
) : AbstractField<O, PrimitiveFieldState<O>>(label, factory), PrimitiveField<O> {

    override fun set(value: O) {
        val res = validator.validate(value)
        name.set(value)
        state.value = state.value.copy(
            output = res.value,
            feedbacks = Feedbacks(res.toWarnings())
        )
    }

    override fun PrimitiveFieldState<O>.with(
        hidden: Boolean,
        feedbacks: Feedbacks
    ) = copy(hidden = hidden, feedbacks = feedbacks)

    override fun cleared() = initial.copy(output = null)

    override val initial: PrimitiveFieldState<O> = PrimitiveFieldState(
        name = name.name,
        label = Label(label, this.validator.required),
        hidden = hidden,
        hint = hint,
        required = this.validator.required,
        output = value,
        feedbacks = Feedbacks(iEmptyList()),
    )
}