package symphony

import kollections.toIList
import kollections.List
import neat.ValidationFactory
import neat.Validator
import neat.Validators
import symphony.internal.Changer
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
    onChange: Changer<List<T>>? = null,
    factory: ValidationFactory<List<T>>? = null
): MultiChoiceField<T> = getOrCreate(name) {
    MultiChoiceFieldImpl(name, label, value, items.toIList(), mapper, hidden, hint, onChange, factory)
}