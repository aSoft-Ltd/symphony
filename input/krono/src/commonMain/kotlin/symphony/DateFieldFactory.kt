package symphony

import krono.LocalDate
import neat.ValidationFactory
import symphony.internal.Changer
import symphony.internal.DateFieldImpl
import kotlin.reflect.KMutableProperty0

fun <D : LocalDate?> Fields<*>.date(
    name: KMutableProperty0<D>,
    label: String = name.name,
    value: D = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    onChange: Changer<D>? = null,
    factory: ValidationFactory<D>? = null
): DateField<D> = getOrCreate(name) {
    DateFieldImpl(name, label, value, hidden, hint, onChange, factory)
}