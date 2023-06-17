@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import symphony.internal.text.EmailInputFieldImpl
import kotlin.reflect.KMutableProperty0

inline fun EmailInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
): BasicTextInputField = EmailInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxLength = maxLength,
    minLength = minLength,
    validator = validator,
)

inline fun Fields<*>.email(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = getOrCreate(name) {
    EmailInputField(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)
}

inline fun Fields<*>.email(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    hint: String = label,
    value: String? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = email(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}

inline fun Fields<*>.email(
    name: KMutableProperty0<String>,
    label: String = name.name,
    hint: String = label,
    value: String = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = email(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}