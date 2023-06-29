@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import neat.ValidationFactory
import neat.Validator
import neat.custom
import symphony.FieldState
import symphony.Feedbacks
import symphony.Field
import symphony.Visibility
import symphony.properties.Settable
import kotlin.js.JsExport

abstract class AbstractField<O, S : FieldState<O>>(
    label: String,
    factory: ValidationFactory<O>?
) : AbstractHideable(), Field<O, S>, Settable<O> {

    protected val validator: Validator<@UnsafeVariance O> = custom<O>(label).configure(factory)

    override fun validate() = validator.validate(output)

    override fun finish() {
        state.stopAll()
        state.history.clear()
    }

    override fun reset() {
        state.value = initial
    }

    override fun clear() {
        set(null)
        state.history.clear()
    }

    protected abstract fun S.with(
        visibility: Visibility = this.visibility,
        feedbacks: Feedbacks = this.feedbacks
    ): S

    override fun setVisibility(v: Visibility) {
        state.value = state.value.with(visibility = v)
    }

    abstract val initial: S

    override val state: MutableLive<S> by lazy { mutableLiveOf(initial) }
}