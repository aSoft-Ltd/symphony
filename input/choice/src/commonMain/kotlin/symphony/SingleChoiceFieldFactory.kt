package symphony

import kollections.toIList
import neat.Validator
import neat.Validators
import symphony.internal.SingleChoiceFieldImpl
import symphony.internal.TransformingSingleChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T> Fields<*>.selectSingle(
    name: KMutableProperty0<T>,
    items: Collection<T & Any>,
    mapper: (T & Any) -> Option,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<T>.() -> Validator<T>)? = null
): SingleChoiceField<T> = getOrCreate(name) {
    SingleChoiceFieldImpl(name, label, value, items.toIList(), mapper, hidden, hint, validator)
}

fun <I, O> Fields<*>.selectSingle(
    name: KMutableProperty0<O>,
    items: Collection<I>,
    transformer: (I) -> O,
    mapper: (I) -> Option,
    label: String = name.name,
    value: O = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<O>.() -> Validator<O>)? = null
): TransformingSingleChoiceField<I, O> = getOrCreate(name) {
    TransformingSingleChoiceFieldImpl(name, label, value, transformer, items.toIList(), mapper, hidden, hint, validator)
}