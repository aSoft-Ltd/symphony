@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.mutableLiveOf
import kollections.iEmptyList
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.Feedbacks
import symphony.Range
import symphony.RangeField
import symphony.RangeFieldState
import symphony.Visibility
import symphony.toErrors
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

open class AbstractRangeField<O : Any>(
    private val property: KMutableProperty0<Range<O>?>,
    label: String,
    visibility: Visibility,
    private val onChange: Changer<Range<O>>?,
    factory: ValidationFactory<Range<O>>?
) : AbstractHideable(), RangeField<O> {

    protected val validator = custom<Range<O>>(label).configure(factory)

    override fun setStart(value: O?) = validateAndNotify(state.value.copy(start = value))

    override fun setEnd(value: O?) = validateAndNotify(state.value.copy(end = value))

    override fun clear() = validateAndNotify(state.value.copy(start = null, end = null))

    override fun finish() {
        state.stopAll()
        state.history.clear()
    }

    override fun reset() = validateAndNotify(initial)

    private fun validateAndNotify(s: State<O>) {
        val res = validator.validate(s.output)
        property.set(s.output)
        state.value = s.copy(feedbacks = Feedbacks(res.toWarnings()))
        onChange?.invoke(s.output)
    }

    override fun validate() = validator.validate(output)

    override fun validateToErrors(): Validity<Range<O>> {
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

    data class State<out O : Any>(
        val name: String,
        override val start: O?,
        override val end: O?,
        override val visibility: Visibility,
        override val required: Boolean,
        override val feedbacks: Feedbacks
    ) : RangeFieldState<O> {
        override val input get() = Range(start, end)
        override val output get() = if (start != null && end != null) Range(start, end) else null
    }

    private val initial = State(
        name = property.name,
        required = this.validator.required,
        start = property.get()?.start,
        end = property.get()?.end,
        visibility = visibility,
        feedbacks = Feedbacks(iEmptyList()),
    )

    override val state = mutableLiveOf(initial)

    override val output get() = state.value.output
    override val required get() = state.value.required
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks
}