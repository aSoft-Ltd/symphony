package symphony

import kollections.List
import neat.ValidationFactory
import symphony.internal.Changer
import symphony.internal.ListFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.list(
    name: KMutableProperty0<List<T>>,
    label: String = name.name,
    value: List<T> = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): ListField<T> = getOrCreate(name) {
    ListFieldImpl(name, label, value, hidden, hint, onChange, factory)
}