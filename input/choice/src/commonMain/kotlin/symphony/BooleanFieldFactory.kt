package symphony

import neat.ValidationFactory
import neat.Validator
import neat.Validators
import symphony.internal.BooleanFieldImpl
import symphony.internal.Changer
import kotlin.reflect.KMutableProperty0

fun <T : Boolean?> Fields<*>.boolean(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    onChange: Changer<T>? = null,
    factory: ValidationFactory<T>? = null
): BooleanField<T> = getOrCreate(name) {
    BooleanFieldImpl(name, label, value, hidden, hint, onChange, factory)
}