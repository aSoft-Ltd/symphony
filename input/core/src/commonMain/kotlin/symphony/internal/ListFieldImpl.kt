package symphony.internal

import cinematic.mutableLiveOf
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.Changer
import symphony.ErrorFeedback
import symphony.Feedbacks
import symphony.Label
import symphony.ListField
import symphony.Visibility
import symphony.toErrors
import symphony.toWarnings

open class ListFieldImpl<E>(
    private val backer: FieldBacker<MutableList<E>>,
    value: List<E>,
    label: String,
    visibility: Visibility,
    private val onChange: Changer<List<E>>?,
    factory: ValidationFactory<List<E>>?
) : AbstractHideable(), ListField<E> {

    protected val validator = custom<List<E>>(label).configure(factory)

    private val backed get() = backer.asProp?.get() ?: state.value.output

    override fun add(item: E) {
        backed.add(item)
        validateAndNotify()
    }

    override fun addAll(items: List<E>) {
        backed.addAll(items)
        validateAndNotify()
    }

    override fun addAll(items: Array<E>) = addAll(items.toList())

    override fun remove(item: E) {
        backed.remove(item)
        validateAndNotify()
    }

    override fun removeAll(items: List<E>?) {
        backed.removeAll(items ?: output)
        validateAndNotify()
    }

    override fun errors(errors: List<String>) {
        if (errors.isEmpty()) return
        val feedbacks = state.value.feedbacks.items + errors.map { ErrorFeedback(it) }
        state.value = state.value.copy(feedbacks = Feedbacks(feedbacks))
    }

    override fun removeAll(items: Array<E>?) {
        removeAll(items?.toList())
    }

    override fun update(item: E, updater: () -> E) {
        val idx = output.indexOf(item)
        if (idx < 0) return
        backed.apply {
            remove(item)
            add(idx, updater())
        }
        validateAndNotify()
    }

    private val initial = LIstFieldImplState(
        label = Label(label, validator.required),
        required = validator.required,
        output = value.toMutableList(),
        visibility = visibility,
        feedbacks = Feedbacks(emptyList()),
    )

    override val state = mutableLiveOf(initial)

    override fun validate() = validator.validate(backed)

    override fun validateToErrors(): Validity<List<E>> {
        val res = validator.validate(backed)
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
        backed.clear()
        validateAndNotify()
    }

    private fun validateAndNotify() {
        val res = validator.validate(backed)
        state.value = state.value.copy(
            output = backed,
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(backed)
    }

    override fun finish() {
        state.stopAll()
        state.history.clear()
    }

    override fun reset() {
        backed.apply {
            clear()
            addAll(initial.output)
        }
        val res = validator.validate(backed)
        state.value = state.value.copy(
            output = backed,
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(backed)
        state.value = initial
    }

    override val output get() = backed
    override val label get() = state.value.label
    override val required get() = state.value.required
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks
}