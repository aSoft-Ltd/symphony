package symphony

import epsilon.Blob
import kollections.List
import neat.ValidationFactory
import symphony.internal.AbstractBaseField
import symphony.internal.Changer
import kotlin.reflect.KMutableProperty0

fun <T : Blob> Fields<*>.files(
    name: KMutableProperty0<List<T>>,
    label: String = name.name,
    value: List<T> = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): ListField<T> = list(name, label, value, hint, hidden, onChange, factory)

fun <T : Blob?> Fields<*>.file(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    onChange: Changer<T>? = null,
    factory: ValidationFactory<T>? = null
): BaseField<T> = getOrCreate(name) {
    AbstractBaseField(name, label, value, hidden, hint, onChange, factory)
}