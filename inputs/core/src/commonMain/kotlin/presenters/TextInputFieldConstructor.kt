@file:Suppress("NOTHING_TO_INLINE")

package presenters

import presenters.internal.text.TextInputFieldImpl
import kotlin.reflect.KProperty

@Deprecated("use symphony")
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

@Deprecated("use symphony")
inline fun Fields.text(
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

@Deprecated("use symphony")
inline fun Fields.text(
    name: KProperty<String?>,
    label: String = name.name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    noinline validator: ((String?) -> Unit)? = null
) = text(name.name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)