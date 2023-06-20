package symphony

import kollections.toIList
import neat.Validator
import neat.Validators
import symphony.internal.SingleChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T> Fields<*>.selectSingle(
    name: KMutableProperty0<T>,
    items: Collection<T>,
    mapper: (T) -> Option,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
): SingleChoiceField<T> = getOrCreate(name) {
    SingleChoiceFieldImpl(name, label, value, items.toIList(), mapper, hidden, hint, validator)
}