package symphony.internal

import geo.GeoLocation
import neat.ValidationFactory
import symphony.Changer
import symphony.LocationField
import symphony.LocationProvider
import symphony.Visibility
import symphony.internal.transforming.BaseTransformingFieldImpl
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class LocationFieldImpl(
    name: KMutableProperty0<GeoLocation?>,
    override val provider: LocationProvider,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<GeoLocation>?,
    factory: ValidationFactory<GeoLocation>?
) : BaseTransformingFieldImpl<String, GeoLocation>(name, label, visibility, hint, provider::transform, onChange, factory), LocationField {}