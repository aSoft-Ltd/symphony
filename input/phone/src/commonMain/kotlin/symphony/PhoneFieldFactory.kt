package symphony

import nation.Country
import neat.ValidationFactory
import symphony.internal.PhoneFieldImpl
import kotlin.reflect.KMutableProperty0

fun Fields<*>.phone(
    name: KMutableProperty0<PhoneOutput?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = VisibleVisibility,
    country: Country? = name.get()?.country,
    filter: (Country, key: String) -> Boolean = { c, key ->
        val label = c.name.contains(key, ignoreCase = true)
        val code = "+${c.dialingCode}".contains(key, ignoreCase = true)
        label || code
    },
    onChange: Changer<PhoneOutput>? = null,
    factory: ValidationFactory<PhoneOutput>? = null
): PhoneField = getOrCreate(name) {
    PhoneFieldImpl(name, label, filter, visibility, hint, country, onChange, factory)
}