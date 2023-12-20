package symphony

import neat.ValidationFactory
import symphony.internal.DoubleFieldImpl
import symphony.internal.FieldBacker
import symphony.internal.IntegerFieldImpl
import symphony.internal.LongFieldImpl
import kotlin.reflect.KMutableProperty0

fun DoubleField(
    name: KMutableProperty0<Double?>,
    value: Double? = name.get(),
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Double>? = null,
    factory: ValidationFactory<Double>? = null
): NumberField<Double> {
    return DoubleFieldImpl(FieldBacker.Prop(name), value, label, visibility, hint, onChange, factory)
}

fun DoubleField(
    name: String,
    value: Double? = null,
    label: String = name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Double>? = null,
    factory: ValidationFactory<Double>? = null
): NumberField<Double> {
    return DoubleFieldImpl(FieldBacker.Name(name), value, label, visibility, hint, onChange, factory)
}

fun Fields<*>.double(
    name: KMutableProperty0<Double?>,
    value: Double? = name.get(),
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Double>? = null,
    factory: ValidationFactory<Double>? = null
): NumberField<Double> = getOrCreate(name) {
    DoubleFieldImpl(FieldBacker.Prop(name), value, label, visibility, hint, onChange, factory)
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