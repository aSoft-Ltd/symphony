@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import liquid.NumberFormatter
import symphony.internal.DoubleInputFieldImpl
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

inline fun DoubleInputField(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Double? = null,
    min: Double? = null,
    step: Double? = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
): NumberInputField<Double> = DoubleInputFieldImpl(
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

inline fun Fields<*>.double(
    name: String,
    label: String = name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Double? = null,
    min: Double? = null,
    step: Double? = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
) = getOrCreate(name) {
    DoubleInputField(name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator)
}

@JvmName("optionalDouble")
inline fun Fields<*>.double(
    name: KMutableProperty0<Double?>,
    label: String = name.name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Double? = null,
    min: Double? = null,
    step: Double? = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
) = double(name.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}

inline fun Fields<*>.double(
    name: KMutableProperty0<Double>,
    label: String = name.name,
    hint: String? = label,
    value: Double? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = true,
    formatter: NumberFormatter? = NumberFormatter(),
    max: Double? = null,
    min: Double? = null,
    step: Double? = 0.1,
    noinline validator: ((Double?) -> Unit)? = null
) = double(name.name, label, hint, value, isReadonly, isRequired, formatter, max, min, step, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setAndUpdate(it.output) }
}