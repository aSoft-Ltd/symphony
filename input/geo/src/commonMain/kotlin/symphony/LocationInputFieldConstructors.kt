@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import geo.GeoLocation
import symphony.internal.LocationInputFieldImpl
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

inline fun LocationInputField(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: GeoLocation? = null,
    isReadonly: Boolean = false,
    noinline validator: ((GeoLocation?) -> Unit)? = null
): LocationInputField = LocationInputFieldImpl(name, isRequired, Label(label, isRequired), hint, value, isReadonly, validator)

inline fun Fields<*>.location(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: GeoLocation? = null,
    isReadonly: Boolean = false,
    noinline validator: ((GeoLocation?) -> Unit)? = null
) = getOrCreate(name) {
    LocationInputField(name, isRequired, label, hint, value, isReadonly, validator)
}

inline fun Fields<*>.location(
    name: KMutableProperty0<GeoLocation?>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: GeoLocation? = name.get(),
    isReadonly: Boolean = false,
    noinline validator: ((GeoLocation?) -> Unit)? = null
) = location(name.name, isRequired, label, hint, value, isReadonly, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}

inline fun Fields<*>.location(
    name: KMutableProperty0<GeoLocation>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: GeoLocation = name.get(),
    isReadonly: Boolean = false,
    noinline validator: ((GeoLocation?) -> Unit)? = null
) = location(name.name, isRequired, label, hint, value, isReadonly, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}