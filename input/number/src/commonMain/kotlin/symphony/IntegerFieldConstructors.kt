@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import liquid.NumberFormatter
import symphony.internal.IntegerInputFieldImpl
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0

inline fun IntegerInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Int? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
): NumberInputField<Int> = IntegerInputFieldImpl(
    name = name,
    label = Label(label, isReadonly),
    hint = hint ?: name,
    value = value,
    formatter = formatter,
    isReadonly = isReadonly,
    isRequired = isRequired,
    max = max,
    min = min,
    step = step,
    validator = validator,
)

inline fun Fields<*>.integer(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Int? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
) = getOrCreate(name) {
    IntegerInputField(name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)
}

@JvmName("optionalInteger")
inline fun Fields<*>.integer(
    name: KMutableProperty0<Int?>,
    label: String = name.name,
    hint: String? = label,
    value: Int? = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
) = integer(name.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}

inline fun Fields<*>.integer(
    name: KMutableProperty0<Int>,
    label: String = name.name,
    hint: String? = label,
    value: Int = name.get(),
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Int? = null,
    min: Int? = null,
    step: Int = 1,
    noinline validator: ((Int?) -> Unit)? = null
) = integer(name.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}