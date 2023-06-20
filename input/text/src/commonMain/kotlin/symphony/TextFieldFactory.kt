package symphony

import neat.Validator
import neat.Validators
import neat.min
import neat.notBlank
import symphony.internal.TextFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : String?> TextField(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
): TextField<T> = TextFieldImpl(
    name = name,
    label = label,
    value = value,
    hidden = hidden,
    hint = hint,
    validator = validator
)

fun <T : String?> Fields<*>.text(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
) = getOrCreate(name) { TextField(name, label, value, hint, hidden, validator) }

fun <T : String?> Fields<*>.name(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
) = text(name, label, value, hint, hidden) {
    min(2)
    notBlank()
    configure(validator)
}