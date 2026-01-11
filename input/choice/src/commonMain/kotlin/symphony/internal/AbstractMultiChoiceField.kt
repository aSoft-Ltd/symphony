@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import cinematic.Live
import cinematic.MutableLive
import cinematic.mutableLiveOf
import kotlinx.JsExport
import neat.ValidationFactory
import neat.Validity
import neat.custom
import symphony.BaseFieldState
import symphony.Changer
import symphony.Feedbacks
import symphony.Field
import symphony.MultiChoiceFieldState
import symphony.Visibility
import symphony.properties.Settable
import symphony.toErrors
import symphony.toWarnings
import symphony.internal.MultiChoiceFieldStateImpl as State

abstract class AbstractMultiChoiceField<O>(
    private val backer: FieldBacker<MutableList<O>>,
    label: String,
    private val onChange: Changer<List<O>>?,
    factory: ValidationFactory<List<O>>?
) : AbstractHideable(), Field<List<O>, MultiChoiceFieldState<O>>, BaseFieldState<List<O>>, Settable<List<O>> {

    protected val validator = custom<List<O>>(label).configure(factory)

    override fun set(value: List<O>?) {
        val res = validator.validate(value)
        val output = res.value
        backer.asProp?.set(output.toMutableList())
        state.value = state.value.copy(
            output = output,
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(output)
    }

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