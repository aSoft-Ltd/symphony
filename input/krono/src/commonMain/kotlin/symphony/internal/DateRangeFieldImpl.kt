package symphony.internal

import krono.LocalDate
import krono.LocalDateOrNull
import neat.ValidationFactory
import symphony.DateRangeField
import symphony.Range
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class DateRangeFieldImpl<R : Range<LocalDate>?>(
    name: KMutableProperty0<R>,
    label: String,
    value: R,
    hidden: Boolean,
    hint: String,
    factory: ValidationFactory<Range<LocalDate>>?
) : AbstractRangeField<LocalDate, R>(name, label, value, hidden, hint, factory), DateRangeField<R> {
    override fun setStart(iso: String) = setStart(LocalDateOrNull(iso))
    override fun setEnd(iso: String) = setEnd(LocalDateOrNull(iso))
}