package symphony.internal

import geo.GeoLocation
import neat.ValidationFactory
import symphony.LocationField
import symphony.LocationProvider
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class LocationFieldImpl<I, O : GeoLocation?>(
    name: KMutableProperty0<O>,
    override val provider: LocationProvider<I, O>,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    factory: ValidationFactory<O>?
) : AbstractTransformingField<I, O>(name, provider::transform, label, value, hidden, hint, factory), LocationField<I, O>