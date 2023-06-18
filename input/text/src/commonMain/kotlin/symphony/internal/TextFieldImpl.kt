package symphony.internal

import cinematic.mutableLiveOf
import kollections.iEmptyList
import symphony.Feedbacks
import symphony.Label
import symphony.TextField
import symphony.TextFieldState
import symphony.properties.Settable

@PublishedApi
internal class TextFieldImpl(
    name: String,
    label: Label,
    value: String,
    hidden: Boolean,
    hint: String,
    required: Boolean,
) : TextField, Settable<String> {

    private val initial = TextFieldState(
        name = name,
        label = label,
        hint = hint,
        input = value,
        output = value,
        hidden = hidden,
        required = required,
        suggestions = iEmptyList(),
        feedback = Feedbacks(iEmptyList())
    )

    override val state = mutableLiveOf(initial)

    override fun set(value: String?) {
        state.value = state.value.copy(output = value ?: "")
    }

    override fun hide(hide: Boolean?) {
        state.value = state.value.copy(hidden = hide == true)
    }

    override fun show(show: Boolean?) {
        state.value = state.value.copy(hidden = show != true)
    }

    override fun stop() {
        state.stopAll()
    }

    override fun clear() {
        state.value = state.value.copy(input = "", output = "")
    }

    override fun reset() {
        state.value = initial
    }

    fun type(text: String) {
        val old = state.value.input
        for (i in 0..text.lastIndex) {
            set(old + text.substring(0..i))
        }
    }
}