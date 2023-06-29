package symphony

import kollections.toIList
import neat.ValidationFactory
import symphony.internal.SingleChoiceFieldImpl
import symphony.internal.TransformingSingleChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T> Fields<*>.selectSingle(
    name: KMutableProperty0<T?>,
    items: Collection<T & Any>,
    mapper: (T & Any) -> Option,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<T>? = null,
    factory: ValidationFactory<T>? = null
): SingleChoiceField<T> = getOrCreate(name) {
    SingleChoiceFieldImpl(name, label, items.toIList(), mapper, visibility, hint, onChange, factory)
}

fun <I, O> Fields<*>.selectSingle(
    name: KMutableProperty0<O?>,
    items: Collection<I & Any>,
    transformer: (I?) -> O?,
    mapper: (I) -> Option,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<O>? = null,
    factory: ValidationFactory<O>? = null
): TransformingSingleChoiceField<I, O> = getOrCreate(name) {
    TransformingSingleChoiceFieldImpl(name, label, transformer, items.toIList(), mapper, visibility, hint, onChange, factory)
}