package symphony.internal

import krono.LocalDate
import krono.LocalDateOrNull
import neat.ValidationFactory
import symphony.DateField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class DateFieldImpl<D : LocalDate?>(
    name: KMutableProperty0<D>,
    label: String,
    value: D,
    hidden: Boolean,
    hint: String,
    onChange: Changer<D>?,
    factory: ValidationFactory<D>?
) : AbstractBaseField<D>(name, label, value, hidden, hint, onChange, factory), DateField<D> {
    override fun set(iso: String) = set(LocalDateOrNull(iso) as D)
}