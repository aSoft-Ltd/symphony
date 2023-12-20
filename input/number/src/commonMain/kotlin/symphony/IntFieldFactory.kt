package symphony

import neat.ValidationFactory
import symphony.internal.FieldBacker
import symphony.internal.IntegerFieldImpl
import kotlin.reflect.KMutableProperty0

fun IntField(
    name: KMutableProperty0<Int?>,
    value: Int? = name.get(),
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Int>? = null,
    factory: ValidationFactory<Int>? = null
): NumberField<Int> {
    return IntegerFieldImpl(FieldBacker.Prop(name), value, label, visibility, hint, onChange, factory)
}

fun IntField(
    name: String,
    value: Int? = null,
    label: String = name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Int>? = null,
    factory: ValidationFactory<Int>? = null
): NumberField<Int> {
    return IntegerFieldImpl(FieldBacker.Name(name), value, label, visibility, hint, onChange, factory)
}

fun Fields<*>.integer(
    name: KMutableProperty0<Int?>,
    value: Int? = name.get(),
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Int>? = null,
    factory: ValidationFactory<Int>? = null
): NumberField<Int> = getOrCreate(name) {
    IntegerFieldImpl(FieldBacker.Prop(name), value, label, visibility, hint, onChange, factory)
}