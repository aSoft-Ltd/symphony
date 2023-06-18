@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import epsilon.FileBlob
import symphony.internal.SingleFileInputFieldImpl
import kotlin.reflect.KMutableProperty0

inline fun SingleFileInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: FileBlob? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((FileBlob?) -> Unit)? = null
): SingleFileInputField = SingleFileInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    isRequired = isRequired,
    isReadonly = isReadonly,
    validator = validator
)

inline fun Fields<*>.file(
    name: String,
    label: String = name,
    hint: String = label,
    value: FileBlob? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((FileBlob?) -> Unit)? = null
): SingleFileInputField = getOrCreate(name) {
    SingleFileInputField(name, label, hint, value, isReadonly, isRequired, validator)
}

inline fun Fields<*>.file(
    name: KMutableProperty0<FileBlob?>,
    label: String = name.name,
    hint: String = label,
    value: FileBlob? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((FileBlob?) -> Unit)? = null
) = file(name.name, label, hint, value, isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}

inline fun Fields<*>.file(
    name: KMutableProperty0<FileBlob>,
    label: String = name.name,
    hint: String = label,
    value: FileBlob = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    noinline validator: ((FileBlob?) -> Unit)? = null
) = file(name.name, label, hint, value, isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}