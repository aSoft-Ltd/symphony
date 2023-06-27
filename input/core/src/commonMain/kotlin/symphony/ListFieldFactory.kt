package symphony

import kollections.List
import kollections.MutableList
import neat.ValidationFactory
import symphony.internal.Changer
import symphony.internal.ListFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.list(
    name: KMutableProperty0<MutableList<T>>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): ListField<T> = getOrCreate(name) {
    ListFieldImpl(name, label, visibility, onChange, factory)
}