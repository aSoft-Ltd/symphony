@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kollections.List
import kollections.iEmptyList
import kollections.serializers.ListSerializer
import kollections.toIList
import kotlinx.serialization.serializer
import symphony.internal.ListInputFieldImpl
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
    name: KProperty<Collection<E>?>,
    label: String = name.name,
    hint: String = label,
    value: Collection<E>? = null,
    isRequired: Boolean = false,
    isReadonly: Boolean = false,
    maxItems: Int? = null,
    minItems: Int? = null,
    noinline validator: ((List<E>) -> Unit)? = null
) = list(name.name, label, hint, value, isRequired, isReadonly, maxItems, minItems, validator)