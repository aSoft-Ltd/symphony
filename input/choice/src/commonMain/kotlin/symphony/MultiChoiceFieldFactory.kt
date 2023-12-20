package symphony

import kollections.List
import kollections.Collection
import kollections.MutableList
import neat.ValidationFactory
import symphony.internal.MultiChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.selectMany(
    name: KMutableProperty0<MutableList<T>>,
    items: Collection<T>,
    mapper: (T) -> Option,
    label: String = name.name,
    visibility: Visibility = Visibilities.Hidden,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): MultiChoiceField<T> = getOrCreate(name) {
    MultiChoiceFieldImpl(name, label, items, mapper, visibility, onChange, factory)
}