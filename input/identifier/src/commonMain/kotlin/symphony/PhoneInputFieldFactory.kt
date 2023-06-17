@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import symphony.internal.text.PhoneInputFieldImpl
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

inline fun PhoneInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((PhoneNumber?) -> Unit)? = null
): PhoneInputField = PhoneInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)

inline fun Fields<*>.phone(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((PhoneNumber?) -> Unit)? = null
) = getOrCreate(name) {
    PhoneInputField(name, label, hint, value, isReadonly, isRequired, validator)
}

inline fun Fields<*>.phone(
    name: KMutableProperty0<PhoneNumber?>,
    label: String = name.name,
    hint: String = label,
    value: PhoneNumber? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((PhoneNumber?) -> Unit)? = null
) = phone(name.name, label, hint, value.toString(), isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}

inline fun Fields<*>.phone(
    name: KMutableProperty0<PhoneNumber>,
    label: String = name.name,
    hint: String = label,
    value: PhoneNumber? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    noinline validator: ((PhoneNumber?) -> Unit)? = null
) = phone(name.name, label, hint, value.toString(), isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}