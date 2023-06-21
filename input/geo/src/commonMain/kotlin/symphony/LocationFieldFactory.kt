package symphony

import geo.GeoLocation
import neat.ValidationFactory
import symphony.internal.LocationFieldImpl
import kotlin.reflect.KMutableProperty0

fun <I, G : GeoLocation?> Fields<*>.location(
    name: KMutableProperty0<G>,
    provider: LocationProvider<I, G>,
    label: String = name.name,
    value: G = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<G>? = null
): LocationField<I, G> = getOrCreate(name) {
    LocationFieldImpl(name, provider, label, value, hidden, hint, factory)
}

fun <G : GeoLocation?> Fields<*>.location(
    name: KMutableProperty0<G>,
    label: String = name.name,
    value: G = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    factory: ValidationFactory<G>? = null
) = location(name, GoogleLocationProvider(), label, value, hint, hidden, factory)