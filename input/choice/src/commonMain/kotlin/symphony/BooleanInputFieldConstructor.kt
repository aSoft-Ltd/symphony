@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import symphony.internal.BooleanInputFieldImpl
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0

inline fun BooleanInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = BooleanInputFieldImpl(
    name = name,
    label = Label(label, isReadonly),
    hint = hint,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator,
)

inline fun Fields<*>.boolean(
    name: String,
    label: String = name,
    hint: String = label,
    value: Boolean? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
): BooleanInputField = getOrCreate(name) {
    BooleanInputField(name, label, hint, value, isReadonly, isRequired, validator)
}

@JvmName("optionalBoolean")
inline fun Fields<*>.boolean(
    name: KMutableProperty0<Boolean?>,
    label: String = name.name,
    hint: String = label,
    value: Boolean? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((Boolean?) -> Unit)? = null
) = boolean(name.name, label, hint, value, isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.set(it.output) }
}

inline fun Fields<*>.boolean(
    name: KMutableProperty0<Boolean>,
    label: String = name.name,
    hint: String = label,
    value: Boolean? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    noinline validator: ((Boolean?) -> Unit)? = null
) = boolean(name.name, label, hint, value, isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setIfNotNull(it.output) }
}