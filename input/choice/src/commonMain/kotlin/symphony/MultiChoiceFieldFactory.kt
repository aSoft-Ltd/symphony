package symphony

import kollections.toIList
import kollections.List
import neat.Validator
import neat.Validators
import symphony.internal.MultiChoiceFieldImpl
import kotlin.reflect.KMutableProperty0

fun <T : Any> Fields<*>.selectMany(
    name: KMutableProperty0<List<T>>,
    items: Collection<T>,
    mapper: (T) -> Option,
    label: String = name.name,
    value: List<T> = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    validator: (Validators<List<T>>.() -> Validator<List<T>>)? = null
): MultiChoiceField<T> = getOrCreate(name) {
    MultiChoiceFieldImpl(name, label, value, items.toIList(), mapper, hidden, hint, validator)
}