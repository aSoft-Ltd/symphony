package symphony

import neat.ValidationFactory
import symphony.internal.BooleanFieldImpl
import kotlin.reflect.KMutableProperty0
import symphony.internal.FieldBacker

fun Fields<*>.boolean(
    name: KMutableProperty0<Boolean?>,
    value: Boolean? = name.get(),
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Boolean>? = null,
    factory: ValidationFactory<Boolean>? = null
): BooleanField = getOrCreate(name) {
    BooleanFieldImpl(FieldBacker.Prop(name), value, label, visibility, hint, onChange, factory)
}

fun BooleanField(
    name: String = "Boolean Field",
    value: Boolean? = null,
    label: String = name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<Boolean>? = null,
    factory: ValidationFactory<Boolean>? = null
) : BooleanField = BooleanFieldImpl(FieldBacker.Name(name), value, label, visibility, hint, onChange, factory)