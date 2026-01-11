package symphony

import neat.ValidationFactory
import symphony.internal.FieldBacker
import symphony.internal.MultiChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.selectMany(
    name: KMutableProperty0<MutableList<T>>,
    items: Collection<T>,
    mapper: (T) -> Option,
    filter: (item: T, key: String) -> Boolean = { item, key -> item.toString().contains(key, ignoreCase = true) },
    searchBy: SearchBy = SearchBy.Filtering,
    label: String = name.name,
    hint: String = label,
    value: List<T> = name.get(),
    visibility: Visibility = Visibilities.Hidden,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): MultiChoiceField<T> = getOrCreate(name) {
    MultiChoiceFieldImpl(FieldBacker.Prop(name as KMutableProperty0<MutableList<T>?>), label, hint, value, items, mapper, filter, searchBy, visibility, onChange, factory)
}

fun <T : Any> MultiChoiceField(
    name: KMutableProperty0<MutableList<T>>,
    items: Collection<T>,
    mapper: (T) -> Option,
    filter: (item: T, key: String) -> Boolean = { item, key -> item.toString().contains(key, ignoreCase = true) },
    searchBy: SearchBy = SearchBy.Filtering,
    label: String = name.name,
    hint: String = label,
    value: List<T> = name.get(),
    visibility: Visibility = Visibilities.Hidden,
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): MultiChoiceField<T> = MultiChoiceFieldImpl(FieldBacker.Prop(name as KMutableProperty0<MutableList<T>?>), label, hint, value, items, mapper, filter, searchBy, visibility, onChange, factory)