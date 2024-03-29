package symphony

import neat.ValidationFactory
import kollections.Collection
import kollections.toList
import symphony.internal.SingleChoiceFieldImpl
import symphony.internal.TransformingSingleChoiceFieldImpl
import kotlin.reflect.KMutableProperty0
import symphony.internal.FieldBacker

fun <T> SingleChoiceField(
    name: String = "",
    items: Collection<T & Any>,
    mapper: (T & Any) -> Option,
    filter: (item: T & Any, key: String) -> Boolean = { item, key -> item.toString().contains(key, ignoreCase = true) },
    searchBy: SearchBy = SearchBy.Filtering,
    value: T? = null,
    label: String = name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<T>? = null,
    factory: ValidationFactory<T>? = null
): SingleChoiceField<T> = SingleChoiceFieldImpl(FieldBacker.Name(name), label, value, items.toList(), mapper, filter, searchBy, visibility, hint, onChange, factory)

fun <T> Fields<*>.selectSingle(
    name: KMutableProperty0<T?>,
    items: Collection<T & Any>,
    mapper: (T & Any) -> Option,
    filter: (item: T & Any, key: String) -> Boolean = { item, key -> item.toString().contains(key, ignoreCase = true) },
    searchBy: SearchBy = SearchBy.Filtering,
    label: String = name.name,
    value: T? = name.get(),
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<T>? = null,
    factory: ValidationFactory<T>? = null
): SingleChoiceField<T> = getOrCreate(name) {
    SingleChoiceFieldImpl(FieldBacker.Prop(name), label, value, items.toList(), mapper, filter, searchBy, visibility, hint, onChange, factory)
}

fun <I, O> Fields<*>.selectSingle(
    name: KMutableProperty0<O?>,
    items: Collection<I & Any>,
    transformer: (I?) -> O?,
    mapper: (I) -> Option,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibilities.Visible,
    onChange: Changer<O>? = null,
    factory: ValidationFactory<O>? = null
): TransformingSingleChoiceField<I, O> = getOrCreate(name) {
    TransformingSingleChoiceFieldImpl(name, label, transformer, items.toList(), mapper, visibility, hint, onChange, factory)
}