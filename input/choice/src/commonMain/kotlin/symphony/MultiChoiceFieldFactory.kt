package symphony

import neat.ValidationFactory
import symphony.internal.FieldBacker
import symphony.internal.MultiChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.selectMany(
    name: KMutableProperty0<MutableList<T>>,
    items: Collection<T>,
    mapper: (T) -> Option,
    label: String = name.name,
    value: List<T> = name.get(),
    visibility: Visibility = Visibilities.Hidden,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): MultiChoiceField<T> = getOrCreate(name) {
    MultiChoiceFieldImpl(FieldBacker.Prop(name as KMutableProperty0<MutableList<T>?>), label, value, items, mapper, visibility, onChange, factory)
}