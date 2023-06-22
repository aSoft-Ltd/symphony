package symphony

import neat.ValidationFactory
import kotlin.reflect.KMutableProperty0

fun <T : Double?> Fields<*>.money(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<T>? = null
): NumberField<T> = double(name, label, value, hint, hidden, factory)