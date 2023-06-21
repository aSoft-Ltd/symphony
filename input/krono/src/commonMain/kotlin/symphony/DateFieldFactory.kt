package symphony

import krono.LocalDate
import neat.Validator
import neat.Validators
import symphony.internal.DateFieldImpl
import kotlin.reflect.KMutableProperty0

fun <D : LocalDate?> Fields<*>.date(
    name: KMutableProperty0<D>,
    label: String = name.name,
    value: D = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<D>.() -> Validator<D>)? = null
): DateField<D> = getOrCreate(name) {
    DateFieldImpl(name, label, value, hidden, hint, validator)
}