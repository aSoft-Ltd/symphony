package symphony

import neat.Validator
import neat.Validators
import symphony.internal.DoubleFieldImpl
import symphony.internal.IntegerFieldImpl
import symphony.internal.LongFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Double?> Fields<*>.double(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
): NumberField<T> = getOrCreate(name) {
    DoubleFieldImpl(name, label, value, hidden, hint, validator)
}

fun <T : Long?> Fields<*>.long(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
): NumberField<T> = getOrCreate(name) {
    LongFieldImpl(name, label, value, hidden, hint, validator)
}

fun <T : Int?> Fields<*>.integer(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
): NumberField<T> = getOrCreate(name) {
    IntegerFieldImpl(name, label, value, hidden, hint, validator)
}