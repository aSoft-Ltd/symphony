package symphony.internal

import kollections.iEmptyList
import neat.Validator
import neat.Validators
import neat.required
import symphony.BooleanField
import symphony.Feedbacks
import symphony.PrimitiveFieldState
import symphony.Label
import symphony.PrimitiveField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class BooleanFieldImpl<T : Boolean?>(
    override val name: KMutableProperty0<T>,
    label: String,
    value: T,
    hidden: Boolean,
    hint: String,
    validator: (Validators<T>.() -> Validator<T>)?
) : PrimitiveField<T, T>(name, label, validator), BooleanField<T> {

    override val initial = PrimitiveFieldState(
        name = name.name,
        label = Label(label, this.validator.required),
        hint = hint,
        input = value,
        output = value,
        hidden = hidden,
        required = this.validator.required,
        feedbacks = Feedbacks(iEmptyList())
    )

    override fun toggle() = set((output?.not() ?: false) as T)
}