@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import kotlin.reflect.KMutableProperty0

inline fun PasswordInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
): TextInputField = TextInputField(
    name = name,
    label = label,
    hint = hint,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxLength = maxLength,
    minLength = minLength,
    validator = validator,
)

inline fun Fields<*>.password(
    name: String,
    label: String = name,
    hint: String = label,
    value: String = "",
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = getOrCreate(name) {
    PasswordInputField(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)
}

inline fun Fields<*>.password(
    name: KMutableProperty0<String>,
    label: String = name.name,
    hint: String = label,
    value: String = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = password(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setIfNotNull(it.output) }
}