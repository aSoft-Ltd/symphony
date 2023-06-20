package symphony.internal

import cinematic.mutableLiveOf
import kollections.iEmptyList
import neat.Validator
import neat.Validators
import neat.Validity
import neat.custom
import neat.required
import symphony.Feedbacks
import symphony.Label
import symphony.TextField
import symphony.TextInputState
import symphony.toErrors
import symphony.toWarnings
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class TextFieldImpl<T : String?>(
    val name: KMutableProperty0<T>,
    label: String,
    value: T,
    hidden: Boolean,
    hint: String,
    validator: (Validators<T>.() -> Validator<T>)?
) : TextField<T> {

    private val validator = custom<T>(label).configure(validator)

    private val initial = TextInputState(
        name = name.name,
        label = Label(label, this.validator.required),
        hint = hint,
        input = value,
        output = value,
        hidden = hidden,
        suggestions = iEmptyList(),
        required = this.validator.required,
        feedbacks = Feedbacks(iEmptyList())
    )

    override val state = mutableLiveOf(initial)

    override fun validate() = validator.validate(output)

    override fun set(value: T) {
        val res = validator.validate(value)
        name.set(res.value)
        state.value = state.value.copy(
            input = value,
            output = res.value,
            feedbacks = Feedbacks(res.toWarnings())
        )
    }

    override fun hide(hide: Boolean?) {
        state.value = state.value.copy(hidden = hide == true)
    }

    override fun show(show: Boolean?) {
        state.value = state.value.copy(hidden = show != true)
    }

    override fun validateToErrors(): Validity<T> {
        val res = validator.validate(output)
        state.value = state.value.copy(feedbacks = Feedbacks(res.toErrors()))
        return res
    }

    override fun finish() {
        state.stopAll()
    }

    override fun clear() {
        name.set(initial.output)
        state.value = state.value.copy(input = "", output = initial.output)
    }

    override fun reset() {
        state.value = initial
    }

//    fun type(text: String) {
//        val old = state.value.input
//        for (i in 0..text.lastIndex) {
//            set(old + text.substring(0..i))
//        }
//    }
}