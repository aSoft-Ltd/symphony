@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import kollections.List
import kollections.iEmptyList
import kollections.serializers.ListSerializer
import kollections.toIList
import kotlinx.serialization.serializer
import symphony.internal.ListInputFieldImpl
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

inline fun <E> ListInputField(
    name: String,
    label: String = name,
    hint: String = label,
    value: Collection<E>? = null,
    isRequired: Boolean = false,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null,
    noinline validator: ((List<E>) -> Unit)? = null
): ListInputField<E> = ListInputFieldImpl(
    name = name,
    label = Label(label, isRequired),
    hint = hint,
    value = value?.toIList() ?: iEmptyList(),
    isReadonly = isReadonly,
    isRequired = isRequired,
    maxItems = maxItems,
    minItems = minItems,
    validator = validator
)

inline fun <E> Fields<*>.list(
    name: String,
    label: String = name,
    hint: String = label,
    value: Collection<E>? = null,
    isRequired: Boolean = false,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null,
    noinline validator: ((List<E>) -> Unit)? = null
): ListInputField<E> = getOrCreate(name) {
    ListInputField(name, label, hint, value, isRequired, isReadonly, maxItems, minItems, validator)
}

inline fun <E> Fields<*>.list(
    name: KMutableProperty0<List<E>>,
    label: String = name.name,
    hint: String = label,
    value: Collection<E> = name.get(),
    isRequired: Boolean = false,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null,
    noinline validator: ((List<E>) -> Unit)? = null
) = list(name.name, label, hint, value, isRequired, isReadonly, maxItems, minItems, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}