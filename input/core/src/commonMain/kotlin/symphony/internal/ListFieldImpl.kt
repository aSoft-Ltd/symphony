package symphony.internal

import cinematic.mutableLiveOf
import kollections.List
import kollections.MutableList
import kollections.iEmptyList
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.Changer
import symphony.Feedbacks
import symphony.ListField
import symphony.ListFieldState
import symphony.Visibility
import symphony.toErrors
import symphony.toWarnings
import kotlin.reflect.KProperty0

open class ListFieldImpl<E>(
    private val property: KProperty0<MutableList<E>>,
    label: String,
    visibility: Visibility,
    private val onChange: Changer<List<E>>?,
    factory: ValidationFactory<List<E>>?
) : AbstractHideable(), ListField<E> {

    protected val validator = custom<List<E>>(label).configure(factory)

    override fun add(item: E) {
        property.get().add(item)
        validateAndNotify()
    }

    override fun addAll(items: List<E>) {
        property.get().addAll(items)
        validateAndNotify()
    }

    override fun remove(item: E) {
        property.get().remove(item)
        validateAndNotify()
    }

    override fun removeAll(items: List<E>?) {
        property.get().removeAll(items ?: output)
        validateAndNotify()
    }

    override fun update(item: E, updater: () -> E) {
        val idx = output.indexOf(item)
        if (idx < 0) return
        property.get().apply {
            remove(item)
            add(idx, updater())
        }
        validateAndNotify()
    }

    private val initial = State(
        required = this.validator.required,
        output = property.get(),
        visibility = visibility,
        feedbacks = Feedbacks(iEmptyList()),
    )

    data class State<O>(
        override val visibility: Visibility,
        override val required: Boolean,
        override val output: MutableList<O>,
        override val feedbacks: Feedbacks
    ) : ListFieldState<O>

    override val state = mutableLiveOf(initial)

    override fun validate() = validator.validate(output)

    override fun validateToErrors(): Validity<List<E>> {
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

    override fun clear() {
        property.get().clear()
        validateAndNotify()
    }

    private fun validateAndNotify() {
        val res = validator.validate(property.get())
        state.value = state.value.copy(
            output = property.get(),
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(property.get())
    }

    override fun finish() {
        state.stopAll()
        state.history.clear()
    }

    override fun reset() {
        property.get().apply {
            clear()
            addAll(initial.output)
        }
        val res = validator.validate(property.get())
        state.value = state.value.copy(
            output = property.get(),
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(property.get())
        state.value = initial
    }

    override val output get() = property.get()
    override val required get() = state.value.required
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks
}