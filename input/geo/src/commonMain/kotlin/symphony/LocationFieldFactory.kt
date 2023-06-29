package symphony

import geo.GeoLocation
import neat.ValidationFactory
import symphony.internal.LocationFieldImpl
import kotlin.reflect.KMutableProperty0

fun Fields<*>.location(
    name: KMutableProperty0<GeoLocation?>,
    provider: LocationProvider = GoogleLocationProvider,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<GeoLocation>? = null,
    factory: ValidationFactory<GeoLocation>? = null
): LocationField = getOrCreate(name) {
    LocationFieldImpl(name, provider, label, visibility, hint, onChange, factory)
}