package symphony.internal

import kollections.iEmptyList
import neat.Validator
import neat.Validators
import neat.required
import symphony.Feedbacks
import symphony.PrimitiveFieldState
import symphony.Label
import symphony.PrimitiveField
import symphony.TextField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class TextFieldImpl<T : String?>(
    name: KMutableProperty0<T>,
    label: String,
    value: T,
    hidden: Boolean,
    hint: String,
    validator: (Validators<T>.() -> Validator<T>)?
) : PrimitiveField<String?, T>(name, label, validator), TextField<T> {

    override val initial = PrimitiveFieldState<String?, T>(
        name = name.name,
        label = Label(label, this.validator.required),
        hint = hint,
        input = value,
        output = value,
        hidden = hidden,
        required = this.validator.required,
        feedbacks = Feedbacks(iEmptyList())
    )

    override val output get() = state.value.output
    override val hidden: Boolean get() = state.value.hidden
}