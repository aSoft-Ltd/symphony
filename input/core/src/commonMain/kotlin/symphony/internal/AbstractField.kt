@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import neat.ValidationFactory
import neat.Validator
import neat.Validity
import neat.custom
import symphony.CommonField
import symphony.Feedbacks
import symphony.FieldState
import symphony.Label
import symphony.toErrors
import kotlin.js.JsExport

abstract class AbstractField<out O, out S : FieldState<O>>(
    label: String,
    factory: ValidationFactory<O>?
) : CommonField<O, S> {

    protected val validator: Validator<@UnsafeVariance O> = custom<O>(label).configure(factory)

    override fun validate() = validator.validate(output)

    override fun hide(hide: Boolean?) {
        state.value = state.value.with(hidden = hide == true)
    }

    override fun show(show: Boolean?) {
        state.value = state.value.with(hidden = show != true)
    }

    override fun validateToErrors(): Validity<O> {
        val res = validator.validate(output)
        state.value = state.value.with(feedbacks = Feedbacks(res.toErrors()))
        return res
    }

    override fun finish() {
        state.stopAll()
        state.history.clear()
    }

    override fun reset() {
        state.value = initial
    }

    override fun clear() {
        state.value = cleared()
        state.history.clear()
    }

    protected abstract fun @UnsafeVariance S.with(
        hidden: Boolean = this.hidden,
        feedbacks: Feedbacks = this.feedbacks
    ): S

    protected abstract fun cleared(): S

    abstract val initial: S

    override val state: MutableLive<@UnsafeVariance S> by lazy { mutableLiveOf(initial) }

    override val output: O? get() = state.value.output
    override val label: Label get() = state.value.label
    override val required: Boolean get() = state.value.required
    override val hint: String get() = state.value.hint
    override val hidden: Boolean get() = state.value.hidden
    override val feedbacks: Feedbacks get() = state.value.feedbacks
}