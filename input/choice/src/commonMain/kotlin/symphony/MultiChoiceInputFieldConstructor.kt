@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import kollections.List
import kollections.Set
import kollections.toIList
import kollections.toISet
import symphony.internal.MultiChoiceInputFieldImpl
import kotlin.reflect.KMutableProperty0

inline fun <T : Any> MultiChoiceInputField(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    isRequired: Boolean = false,
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: Collection<T>? = null,
    isReadonly: Boolean = false,
    noinline validator: ((List<T>?) -> Unit)? = null
): MultiChoiceInputField<T> = MultiChoiceInputFieldImpl(
    name = name,
    items = items.toIList(),
    mapper = mapper,
    hint = hint,
    value = value?.toIList(),
    label = Label(label, isReadonly),
    isReadonly = isReadonly,
    isRequired = isRequired,
    validator = validator
)

inline fun <T : Any> Fields<*>.selectMany(
    name: String,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    isRequired: Boolean = false,
    label: String = name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: Collection<T>? = null,
    isReadonly: Boolean = false,
    noinline validator: ((List<T>?) -> Unit)? = null
): MultiChoiceInputField<T> = getOrCreate(name) {
    MultiChoiceInputField(name, items, mapper, isRequired, label, hint, value, isReadonly, validator)
}

inline fun <reified T : Any> Fields<*>.selectMany(
    name: KMutableProperty0<List<T>>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    isRequired: Boolean = false,
    label: String = name.name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: List<T> = name.get(),
    isReadonly: Boolean = false,
    noinline validator: ((List<T>?) -> Unit)? = null
) = selectMany(name.name, items, mapper, isRequired, label, hint, value, isReadonly, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.set(it.output) }
}

inline fun <reified T : Any> Fields<*>.selectMany(
    name: KMutableProperty0<Set<T>>,
    items: Collection<T>,
    noinline mapper: (T) -> Option,
    isRequired: Boolean = false,
    label: String = name.name.replaceFirstChar { it.uppercase() },
    hint: String = label,
    value: Set<T> = name.get(),
    isReadonly: Boolean = false,
    noinline validator: ((List<T>?) -> Unit)? = null
) = selectMany(name.name, items, mapper, isRequired, label, hint, value, isReadonly, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.set(it.output.toISet()) }
}