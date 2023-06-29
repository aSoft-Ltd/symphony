package symphony

import kollections.toIList
import kollections.List
import kollections.MutableList
import neat.ValidationFactory
import symphony.internal.MultiChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.selectMany(
    name: KMutableProperty0<MutableList<T>>,
    items: Collection<T>,
    mapper: (T) -> Option,
    label: String = name.name,
    visibility: Visibility = Visibility.Hidden,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): MultiChoiceField<T> = getOrCreate(name) {
    MultiChoiceFieldImpl(name, label, items.toIList(), mapper, visibility, onChange, factory)
}