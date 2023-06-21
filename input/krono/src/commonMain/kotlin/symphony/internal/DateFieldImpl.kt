package symphony.internal

import krono.LocalDate
import krono.LocalDateOrNull
import neat.Validator
import neat.Validators
import symphony.DateField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class DateFieldImpl<D : LocalDate?>(
    name: KMutableProperty0<D>,
    label: String,
    value: D,
    hidden: Boolean,
    hint: String,
    validator: (Validators<D>.() -> Validator<D>)?
) : AbstractPrimitiveField<D>(name, label, value, hidden, hint, validator), DateField<D> {
    override fun set(iso: String) = set(LocalDateOrNull(iso) as D)
}