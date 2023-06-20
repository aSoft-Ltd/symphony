@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.mutableLiveOf
import kollections.iEmptyList
import neat.Validator
import neat.Validators
import neat.Validity
import neat.custom
import neat.required
import symphony.Feedbacks
import symphony.Label
import symphony.TransformingField
import symphony.TransformingFieldState
import symphony.toErrors
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

open class AbstractTransformingField<I, O>(
    val name: KMutableProperty0<O>,
    override val transformer: (I) -> O,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    validator: (Validators<O>.() -> Validator<O>)?
) : TransformingField<I, O> {
    protected val validator = custom<O>(label).configure(validator)

    override fun validate() = validator.validate(output)

    override fun set(value: I) {
        val o = transformer(value)
        val res = validator.validate(o)
        name.set(o)
        state.value = state.value.copy(
            input = value,
            output = res.value,
            feedbacks = Feedbacks(res.toWarnings())
        )
    }

    override fun hide(hide: Boolean?) {
        state.value = state.value.copy(hidden = hide == true)
    }

    override fun show(show: Boolean?) {
        state.value = state.value.copy(hidden = show != true)
    }

    override fun validateToErrors(): Validity<O> {
        val res = validator.validate(output)
        state.value = state.value.copy(feedbacks = Feedbacks(res.toErrors()))
        return res
    }

    override fun finish() {
        state.stopAll()
    }

    override fun reset() {
        state.value = initial
    }

    override fun clear() {
        state.value = initial.copy(output = null)
    }

    val initial: TransformingFieldState<I, O> = TransformingFieldState(
        name = name.name,
        label = Label(label, this.validator.required),
        hidden = hidden,
        hint = hint,
        required = this.validator.required,
        output = value,
        input = null,
        feedbacks = Feedbacks(iEmptyList()),
    )

    override val state by lazy { mutableLiveOf(initial) }

    override val input get() = state.value.input
    override val output get() = state.value.output
    override val label get() = state.value.label
    override val required get() = state.value.required
    override val hint get() = state.value.hint
    override val hidden get() = state.value.hidden
}