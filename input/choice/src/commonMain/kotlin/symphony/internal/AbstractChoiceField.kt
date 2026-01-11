@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.mutableLiveOf
import kotlinx.JsExport
import neat.ValidationFactory
import neat.Validity
import neat.custom
import symphony.BaseFieldState
import symphony.Changer
import symphony.Feedbacks
import symphony.Field
import symphony.Visibility
import symphony.properties.Settable
import symphony.toErrors
import symphony.toWarnings

abstract class AbstractChoiceField<O>(
    private val backer: FieldBacker<O>,
    label: String,
    private val onChange: Changer<O>?,
    factory: ValidationFactory<O>?
) : AbstractHideable(), Field<O, BaseFieldState<O>>, BaseFieldState<O>, Settable<O> {

    protected val validator = custom<O>(label).configure(factory)

    override fun set(value: O?) {
        val res = validator.validate(value)
        val output = res.value
        backer.asProp?.set(output)
        state.value = state.value.copy(
            output = output,
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(output)
    }

    protected abstract val initial: BaseFieldState<O>


    final override val state by lazy { mutableLiveOf(initial) }

    override fun validate() = validator.validate(output)

    abstract fun BaseFieldState<O>.copy(
        output: O? = state.value.output,
        feedbacks: Feedbacks = state.value.feedbacks,
        visibility: Visibility = state.value.visibility
    ): BaseFieldState<O>

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