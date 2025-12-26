@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.mutableLiveOf
import kotlinx.JsExport
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.BaseField
import symphony.Changer
import symphony.Feedbacks
import symphony.Label
import symphony.Visibility
import symphony.toErrors
import symphony.toWarnings

open class GenericBaseField<O>(
    private val backer: FieldBacker<O>,
    value: O?,
    label: String,
    visibility: Visibility,
    hint: String,
    private val onChange: Changer<O>?,
    factory: ValidationFactory<O>?
) : AbstractHideable(), BaseField<O> {

    protected val validator = custom<O>(label).configure(factory)

    override fun set(value: O?) {
        val v = if(value is String && value.isBlank()) {
            null
        } else {
            value
        }
        val res = validator.validate(v)
        val output = res.value
        backer.asProp?.set(output)
        state.value = state.value.copy(
            output = output,
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(value)
    }

    private val initial = BaseFieldImplState(
        name = backer.name,
        label = Label(label, this.validator.required),
        hint = hint,
        required = this.validator.required,
        output = value,
        visibility = visibility,
        feedbacks = Feedbacks(emptyList()),
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
        backer.asProp?.set(initial.output)
    }

    override val name get() = state.value.name
    override val label get() = state.value.label
    override val hint get() = state.value.hint
    override val output get() = state.value.output
    override val required get() = state.value.required
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks
}