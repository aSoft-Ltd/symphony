@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import kollections.iEmptyList
import neat.ValidationFactory
import neat.required
import symphony.Feedbacks
import symphony.Label
import symphony.TransformingField
import symphony.TransformingFieldState
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

open class AbstractTransformingField<I, O>(
    val name: KMutableProperty0<O>,
    override val transformer: (I) -> O?,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    factory: ValidationFactory<O>?
) : AbstractField<O, TransformingFieldState<I, O>>(label, factory), TransformingField<I, O> {

    override fun set(value: I) {
        val o = transformer(value)
        val res = validator.validate(o)
        if (o != null) name.set(o)
        state.value = state.value.copy(
            input = value,
            output = res.value,
            feedbacks = Feedbacks(res.toWarnings())
        )
    }

    override fun cleared() = initial.copy(input = null, output = null)

    override fun TransformingFieldState<I, O>.with(
        hidden: Boolean,
        feedbacks: Feedbacks
    ) = copy(hidden = hidden, feedbacks = feedbacks)

    override val initial: TransformingFieldState<I, O> = TransformingFieldState(
        name = name.name,
        label = Label(label, this.validator.required),
        hidden = hidden,
        hint = hint,
        required = this.validator.required,
        output = value,
        input = null,
        feedbacks = Feedbacks(iEmptyList()),
    )

    override val input get() = state.value.input
}