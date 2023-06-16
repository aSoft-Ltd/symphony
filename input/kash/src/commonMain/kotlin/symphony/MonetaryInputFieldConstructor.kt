@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import liquid.NumberFormatter
import kash.Monetary
import kash.MoneyFormatter
import symphony.internal.DEFAULT_FORMATTER
import symphony.internal.MonetaryInputFieldImpl
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

inline fun MonetaryInputField(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Monetary? = null,
    formatter: NumberFormatter? = DEFAULT_FORMATTER,
    isReadonly: Boolean = false,
    maxAmount: Monetary? = null,
    minAmount: Monetary? = null,
    stepAmount: Double? = null,
    noinline validator: ((Monetary?) -> Unit)? = null
): MonetaryInputField = MonetaryInputFieldImpl(
    name = name,
    isRequired = isRequired,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    formatter = formatter,
    isReadonly = isReadonly,
    maxAmount = maxAmount,
    minAmount = minAmount,
    stepAmount = stepAmount,
    validator = validator,
)

inline fun Fields<*>.monetary(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Monetary? = null,
    formatter: NumberFormatter? = DEFAULT_FORMATTER,
    isReadonly: Boolean = false,
    maxAmount: Monetary? = null,
    minAmount: Monetary? = null,
    stepAmount: Double? = null,
    noinline validator: ((Monetary?) -> Unit)? = null
): MonetaryInputField = getOrCreate(name) {
    MonetaryInputField(name, isRequired, label, hint, value, formatter, isReadonly, maxAmount, minAmount, stepAmount, validator)
}

inline fun Fields<*>.monetary(
    name: KMutableProperty0<Monetary?>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: Monetary? = null,
    formatter: NumberFormatter? = DEFAULT_FORMATTER,
    isReadonly: Boolean = false,
    maxAmount: Monetary? = null,
    minAmount: Monetary? = null,
    stepAmount: Double? = null,
    noinline validator: ((Monetary?) -> Unit)? = null
) = monetary(name.name, isRequired, label, hint, value, formatter, isReadonly, maxAmount, minAmount, stepAmount, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.set(it.output) }
}