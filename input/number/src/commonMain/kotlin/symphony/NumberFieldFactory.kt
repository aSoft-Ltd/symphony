package symphony

import neat.ValidationFactory
import symphony.internal.Changer
import symphony.internal.DoubleFieldImpl
import symphony.internal.IntegerFieldImpl
import symphony.internal.LongFieldImpl
import kotlin.reflect.KMutableProperty0

fun Fields<*>.double(
    name: KMutableProperty0<Double?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<Double>? = null,
    factory: ValidationFactory<Double>? = null
): NumberField<Double> = getOrCreate(name) {
    DoubleFieldImpl(name, label, visibility, hint, onChange, factory)
}

//fun <I : Double?, O> Fields<*>.double(
//    name: KMutableProperty0<O>,
//    transformer: (I) -> O,
//    label: String = name.name,
//    value: O = name.get(),
//    hint: String = label,
//    hidden: Boolean = false,
//    factory: ValidationFactory<O>? = null
//): TransformingNumberField<I, O> = getOrCreate(name) {
//    TransformingDoubleFieldImpl(name, transformer, label, value, hidden, hint, factory)
//}

fun Fields<*>.long(
    name: KMutableProperty0<Long?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<Long>? = null,
    factory: ValidationFactory<Long>? = null
): NumberField<Long> = getOrCreate(name) {
    LongFieldImpl(name, label, visibility, hint, onChange, factory)
}

fun Fields<*>.integer(
    name: KMutableProperty0<Int?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<Int>? = null,
    factory: ValidationFactory<Int>? = null
): NumberField<Int> = getOrCreate(name) {
    IntegerFieldImpl(name, label, visibility, hint, onChange, factory)
}