package symphony

import neat.ValidationFactory
import symphony.internal.FieldBacker
import symphony.internal.IntegerFieldImpl
import symphony.internal.LongFieldImpl
import kotlin.reflect.KMutableProperty0

fun LongField(
    name: KMutableProperty0<Long?>,
    value: Long? = name.get(),
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Long>? = null,
    factory: ValidationFactory<Long>? = null
): NumberField<Long> {
    return LongFieldImpl(FieldBacker.Prop(name), value, label, visibility, hint, onChange, factory)
}

fun LongField(
    name: String,
    value: Long? = null,
    label: String = name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Long>? = null,
    factory: ValidationFactory<Long>? = null
): NumberField<Long> {
    return LongFieldImpl(FieldBacker.Name(name), value, label, visibility, hint, onChange, factory)
}

fun Fields<*>.long(
    name: KMutableProperty0<Long?>,
    value: Long? = name.get(),
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Long>? = null,
    factory: ValidationFactory<Long>? = null
): NumberField<Long> = getOrCreate(name) {
    LongFieldImpl(FieldBacker.Prop(name), value, label, visibility, hint, onChange, factory)
}