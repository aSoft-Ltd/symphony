@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import kollections.iEmptyList
import neat.ValidationFactory
import neat.required
import symphony.Feedbacks
import symphony.Label
import symphony.Range
import symphony.RangeField
import symphony.RangeFieldState
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

open class AbstractRangeField<O : Any, R : Range<O>?>(
    val name: KMutableProperty0<R>,
    label: String,
    value: R,
    hidden: Boolean,
    hint: String,
    factory: ValidationFactory<Range<O>>?
) : AbstractField<Range<O>, RangeFieldState<O>>(label, factory), RangeField<O, R> {

    override fun set(value: R) {
        val res = validator.validate(value)
        name.set(value)
        state.value = state.value.copy(
            start = res.value.start,
            end = res.value.end,
            feedbacks = Feedbacks(res.toWarnings())
        )
    }

    override fun setStart(value: O?) {
        val s = state.value.copy(start = value)
        val res = validator.validate(s.output)
        name.set(s.output as R)
        state.value = s.copy(feedbacks = Feedbacks(res.toWarnings()))
    }

    override fun setEnd(value: O?) {
        val s = state.value.copy(end = value)
        val res = validator.validate(s.output)
        name.set(s.output as R)
        state.value = s.copy(feedbacks = Feedbacks(res.toWarnings()))
    }

    override fun RangeFieldState<O>.with(
        hidden: Boolean,
        feedbacks: Feedbacks
    ) = copy(hidden = hidden, feedbacks = feedbacks)

    override fun cleared() = initial.copy(start = null, end = null)

    override val initial: RangeFieldState<O> = RangeFieldState(
        name = name.name,
        label = Label(label, this.validator.required),
        hidden = hidden,
        hint = hint,
        required = this.validator.required,
        start = value?.start,
        end = value?.end,
        feedbacks = Feedbacks(iEmptyList()),
    )
}