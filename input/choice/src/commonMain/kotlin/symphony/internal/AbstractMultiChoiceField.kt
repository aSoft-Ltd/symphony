@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.MutableLive
import kotlinx.JsExport
import neat.ValidationFactory
import neat.Validity
import neat.custom
import symphony.BaseFieldState
import symphony.Feedbacks
import symphony.Field
import symphony.MultiChoiceFieldState
import symphony.Visibility
import symphony.properties.Settable
import symphony.toErrors
import symphony.internal.MultiChoiceFieldStateImpl as State

internal abstract class AbstractMultiChoiceField<O>(
    label: String,
    factory: ValidationFactory<List<O>>?
) : AbstractHideable(), Field<List<O>, MultiChoiceFieldState<O>>, BaseFieldState<List<O>>, Settable<List<O>> {

    protected val validator = custom<List<O>>(label).configure(factory)

    protected abstract val initial: State<O>

    abstract override val state: MutableLive<State<O>>

    override fun validate() = validator.validate(output)

    override fun validateToErrors(): Validity<List<O>> {
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