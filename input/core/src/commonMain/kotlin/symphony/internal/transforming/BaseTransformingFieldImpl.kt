package symphony.internal.transforming

import cinematic.mutableLiveOf
import kollections.iEmptyList
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.Feedbacks
import symphony.TransState
import symphony.TransformingField
import symphony.Visibility
import symphony.internal.AbstractHideable
import symphony.Changer
import symphony.toErrors
import symphony.toWarnings
import kotlin.reflect.KMutableProperty0

open class BaseTransformingFieldImpl<I, O>(
    private val property: KMutableProperty0<O?>,
    label: String,
    visibility: Visibility,
    hint: String,
    private val transformer: (I?) -> O?,
    private val onChange: Changer<O>?,
    factory: ValidationFactory<O>?
) : AbstractHideable(), TransformingField<I, O> {

    protected val validator = custom<O>(label).configure(factory)

    override fun set(value: I?) {
        val output = transformer(value)
        val res = validator.validate(output)
        property.set(output)
        state.value = state.value.copy(
            input = value,
            output = property.get(),
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(property.get())
    }

    private val initial = State<I,O>(
        input = null,
        required = this.validator.required,
        output = property.get(),
        visibility = visibility,
        feedbacks = Feedbacks(iEmptyList()),
    )

    override val state = mutableLiveOf(initial)

    override fun validate() = validator.validate(output)

    override fun validateToErrors(): Validity<O> {
        val res = validator.validate(output)
        val errors = res.toErrors()
        if (errors.isNotEmpty()) {
            state.value = state.value.copy(feedbacks = Feedbacks(errors))
        }
        return res
    }

    override fun setVisibility(v: Visibility) {
        state.value = state.value.copy(visibility = v)
    }

    override fun clear() = set(null)

    override fun finish() {
        state.stopAll()
        state.history.clear()
    }

    override fun reset() {
        state.value = initial
    }

    override val input get() = state.value.input
    override val output get() = state.value.output
    override val required get() = state.value.required
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks

    data class State<out I, out O>(
        override val output: O?,
        override val required: Boolean,
        override val visibility: Visibility,
        override val feedbacks: Feedbacks,
        override val input: I?
    ) : TransState<I, O>
}