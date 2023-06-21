package symphony

import epsilon.Blob
import kollections.List
import neat.ValidationFactory
import symphony.internal.AbstractPrimitiveField
import kotlin.reflect.KMutableProperty0

fun <T : Blob> Fields<*>.files(
    name: KMutableProperty0<List<T>>,
    label: String = name.name,
    value: List<T> = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<List<T>>? = null
): ListField<T> = list(name, label, value, hint, hidden, factory)

fun <T : Blob?> Fields<*>.file(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<T>? = null
): PrimitiveField<T> = getOrCreate(name) {
    AbstractPrimitiveField(name, label, value, hidden, hint, factory)
}