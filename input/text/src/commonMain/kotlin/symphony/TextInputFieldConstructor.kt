@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import symphony.internal.TextInputFieldImpl
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0

inline fun TextInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
): TextInputField = TextInputFieldImpl(
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

inline fun Fields<*>.text(
    name: String,
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
): TextInputField = getOrCreate(name) {
    TextInputField(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)
}

@JvmName("optionalText")
@JsName("optionalText")
inline fun Fields<*>.text(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    hint: String = label,
    value: String? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = text(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}

inline fun Fields<*>.text(
    name: KMutableProperty0<String>,
    label: String = name.name,
    hint: String = label,
    value: String? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = text(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}