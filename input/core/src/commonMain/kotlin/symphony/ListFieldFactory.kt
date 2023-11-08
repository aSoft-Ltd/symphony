package symphony

import kollections.List
import kollections.MutableList
import neat.ValidationFactory
import symphony.internal.ListFieldImpl
import kotlin.reflect.KProperty0

fun <T : Any> Fields<*>.list(
    name: KProperty0<MutableList<T>>,
    label: String = name.name,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): ListField<T> = getOrCreate(name) {
    ListFieldImpl(name, label, visibility, onChange, factory)
}