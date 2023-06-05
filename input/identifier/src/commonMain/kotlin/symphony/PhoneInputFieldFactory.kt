@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.text.PhoneInputFieldImpl
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

inline fun Fields.phone(
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

inline fun Fields.phone(
    name: KProperty<Any?>,
    label: String = name.name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((PhoneNumber?) -> Unit)? = null
) = phone(name.name, label, hint, value, isReadonly, isRequired, validator)