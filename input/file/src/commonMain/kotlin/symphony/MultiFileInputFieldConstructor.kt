@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import epsilon.FileBlob
import kollections.List
import symphony.internal.MultiFileInputFieldImpl
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

inline fun MultiFileInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: List<FileBlob>? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((List<FileBlob>?) -> Unit)? = null
): MultiFileInputField = MultiFileInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    isRequired = isRequired,
    isReadonly = isReadonly,
    validator = validator
)

inline fun Fields<*>.files(
    name: String,
    label: String = name,
    hint: String = label,
    value: List<FileBlob>? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((List<FileBlob>?) -> Unit)? = null
): MultiFileInputField = getOrCreate(name) {
    MultiFileInputField(name, label, hint, value, isReadonly, isRequired, validator)
}

inline fun Fields<*>.files(
    name: KMutableProperty0<List<FileBlob>>,
    label: String = name.name,
    hint: String = label,
    value: List<FileBlob> = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((List<FileBlob>?) -> Unit)? = null
) = files(name.name, label, hint, value, isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.set(it.output) }
}