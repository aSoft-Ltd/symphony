package symphony

import kollections.List
import neat.Validator
import neat.Validators
import symphony.internal.ListFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.list(
    name: KMutableProperty0<List<T>>,
    label: String = name.name,
    value: List<T> = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<List<T>>.() -> Validator<List<T>>)? = null
): ListField<T> = getOrCreate(name) {
    ListFieldImpl(name, label, value, hidden, hint, validator)
}