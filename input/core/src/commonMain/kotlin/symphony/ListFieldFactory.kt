package symphony

import neat.ValidationFactory
import symphony.internal.FieldBacker
import symphony.internal.ListFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.list(
    name: KMutableProperty0<MutableList<T>>,
    label: String = name.name,
    value: List<T> = name.get(),
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): ListField<T> = getOrCreate(name) {
    ListFieldImpl(FieldBacker.Prop(name as KMutableProperty0<MutableList<T>?>), value, label, visibility, onChange, factory)
}

fun <T : Any> listFieldOf(
    name: String = "List",
    label: String = name,
    value: List<T> = emptyList(),
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): ListField<T> = ListFieldImpl(FieldBacker.Name(name), value, label, visibility, onChange, factory)