package symphony

import neat.ValidationFactory
import neat.min
import neat.notBlank
import kotlin.reflect.KMutableProperty0
import symphony.internal.FieldBacker
import symphony.internal.GenericBaseField

fun TextField(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibilities.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
): BaseField<String> = GenericBaseField(FieldBacker.Prop(name), name.get(), label, visibility, hint, onChange, factory)

fun TextField(
    name: String,
    value: String? = null,
    label: String = name,
    visibility: Visibility = Visibilities.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
): BaseField<String> = GenericBaseField(FieldBacker.Name(name), value, label, visibility, hint, onChange, factory)

fun Fields<*>.text(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibilities.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = getOrCreate(name) { TextField(name, label, visibility, hint, onChange, factory) }

fun Fields<*>.name(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibilities.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = text(name, label, visibility, hint, onChange) {
    min(2)
    notBlank()
    configure(factory)
}

fun Fields<*>.password(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibilities.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = text(name, label, visibility, hint, onChange, factory)