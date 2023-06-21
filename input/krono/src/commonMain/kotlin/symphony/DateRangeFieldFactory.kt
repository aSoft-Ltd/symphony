package symphony

import krono.LocalDate
import neat.ValidationFactory
import symphony.internal.DateRangeFieldImpl
import kotlin.reflect.KMutableProperty0

fun <R : Range<LocalDate>?> Fields<*>.range(
    name: KMutableProperty0<R>,
    label: String = name.name,
    value: R = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<Range<LocalDate>>? = null
): DateRangeField<R> = getOrCreate(name) {
    DateRangeFieldImpl(name, label, value, hidden, hint, factory)
}