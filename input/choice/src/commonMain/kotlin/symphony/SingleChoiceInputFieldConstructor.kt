@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import kollections.toIList
import symphony.internal.SingleChoiceInputFieldImpl
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0

inline fun <T : Any> SingleChoiceInputField(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((T?) -> Unit)? = null
): SingleChoiceInputField<T> = SingleChoiceInputFieldImpl(
    name = name,
    items = items.toIList(),
    mapper = mapper,
    label = Label(label, isReadonly),
    hint = hint,
    value = value,
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator
)

inline fun <T : Any> Fields<*>.selectSingle(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((T?) -> Unit)? = null
): SingleChoiceInputField<T> = getOrCreate(name) {
    SingleChoiceInputField(name, items, mapper, label, hint, value, isReadonly, isRequired, validator)
}

@JvmName("optionalSelectSingle")
@JsName("_ignore_optionalSelectSingle")
inline fun <T : Any, P : KMutableProperty0<T?>> Fields<*>.selectSingle(
    name: P,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    label: String = name.name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    noinline validator: ((T?) -> Unit)? = null
) = selectSingle(name.name, items, mapper, label, hint, value, isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}


inline fun <T : Any, P : KMutableProperty0<T>> Fields<*>.selectSingle(
    name: P,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    label: String = name.name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: T = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    noinline validator: ((T?) -> Unit)? = null
) = selectSingle(name.name, items, mapper, label, hint, value, isReadonly, isRequired, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(output) }
}