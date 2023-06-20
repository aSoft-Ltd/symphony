@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.mutableLiveOf
import neat.Validator
import neat.Validators
import neat.Validity
import neat.custom
import symphony.Feedbacks
import symphony.Field
import symphony.FieldState
import symphony.properties.Settable
import symphony.toErrors
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

abstract class AbstractField<I, O : I>(
    open val name: KMutableProperty0<O>,
    label: String,
    validator: (Validators<O>.() -> Validator<O>)?
) : Field<FieldState<I, O>>, Settable<O> {
    protected val validator = custom<O>(label).configure(validator)

    override fun validate() = validator.validate(output)

    override fun set(value: O) {
        val res = validator.validate(value)
        name.set(res.value)
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
        state.value = initial
    }

    abstract val initial: FieldState<I, O>

    override val state by lazy { mutableLiveOf(initial) }

    open val output get() = state.value.output
    override val hidden get() = state.value.hidden
}