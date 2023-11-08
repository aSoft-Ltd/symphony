@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.mutableLiveOf
import kollections.iEmptyList
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.BaseField
import symphony.BaseFieldState
import symphony.Changer
import symphony.Feedbacks
import symphony.Label
import symphony.Visibility
import symphony.toErrors
import symphony.toWarnings
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

open class BaseFieldImpl<O>(
    private val property: KMutableProperty0<O?>,
    label: String,
    visibility: Visibility,
    hint: String,
    private val onChange: Changer<O>?,
    factory: ValidationFactory<O>?
) : AbstractHideable(), BaseField<O> {

    protected val validator = custom<O>(label).configure(factory)

    override fun set(value: O?) {
        val res = validator.validate(value)
        val output = res.value
        property.set(output)
        state.value = state.value.copy(
            output = property.get(),
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(property.get())
    }

    private val initial = BaseFieldImplState(
        name = property.name,
        label = Label(label, this.validator.required),
        hint = hint,
        required = this.validator.required,
        output = property.get(),
        visibility = visibility,
        feedbacks = Feedbacks(iEmptyList()),
    )

    final override val state = mutableLiveOf(initial)

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

    override val name get() = state.value.name
    override val label get() = state.value.label
    override val hint get() = state.value.hint
    override val output get() = state.value.output
    override val required get() = state.value.required
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks
}