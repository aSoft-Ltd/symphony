@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal const val DEFAULT_MIN_LENGTH = 2

@PublishedApi
internal const val DEFAULT_IS_REQUIRED: Boolean = true

inline fun Fields<*>.name(
    name: String = "name",
    label: String = name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    maxLength: Int? = null,
    minLength: Int? = DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = null
) = text(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator)

inline fun Fields<*>.name(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    hint: String = label,
    value: String? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = DEFAULT_IS_REQUIRED,
    maxLength: Int? = null,
    minLength: Int? = DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = null
) = text(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.set(it.output) }
}

inline fun Fields<*>.name(
    name: KMutableProperty0<String>,
    label: String = name.name,
    hint: String = label,
    value: String = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = DEFAULT_MIN_LENGTH,
    noinline validator: ((String?) -> Unit)? = null
) = text(name, label, hint, value, isReadonly, isRequired, maxLength, minLength, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setIfNotNull(it.output) }
}