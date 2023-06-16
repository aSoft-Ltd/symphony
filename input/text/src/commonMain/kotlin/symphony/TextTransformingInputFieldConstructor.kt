package symphony

import kotlin.reflect.KProperty

inline fun <reified O : Any> Fields<*>.textTo(
    name: String,
    label: String = name,
    hint: String = label,
    value: O? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<O>? = null,
    noinline validator: ((O?) -> Unit)? = null,
    noinline transformer: (String?) -> O?
): TransformingInputField<String, O> = getOrCreate(name) {
    TransformingInputField(name, label, hint, value, isReadonly, isRequired, formatter, validator, transformer)
}

inline fun <reified O : Any> Fields<*>.textTo(
    name: KProperty<O?>,
    label: String = name.name,
    hint: String = label,
    value: O? = null,
    isReadonly: Boolean = false,
    isRequired: Boolean = false,
    formatter: Formatter<O>? = null,
    noinline validator: ((O?) -> Unit)? = null,
    noinline transformer: (String?) -> O?
) = textTo(name.name, label, hint, value, isReadonly, isRequired, formatter, validator, transformer)