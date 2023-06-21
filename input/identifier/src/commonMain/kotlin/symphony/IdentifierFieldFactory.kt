package symphony

import neat.ValidationFactory
import symphony.internal.PhoneFieldImpl
import symphony.validators.email
import kotlin.reflect.KMutableProperty0

fun <T : String?> Fields<*>.email(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<T>? = null
) = text(name, label, value, hint, hidden) {
    email()
    configure(factory)
}

fun <T : PhoneOutput?> Fields<*>.phone(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<T>? = null
): PhoneField<T> = getOrCreate(name) {
    PhoneFieldImpl(name, label, value, hidden, hint, factory)
}