@file:Suppress("NOTHING_TO_INLINE")

package symphony

import neat.Validator
import neat.Validators
import neat.min
import kotlin.reflect.KMutableProperty0

inline fun <T : String?> Fields<*>.name(
    name: KMutableProperty0<T>,
    label: String = name.name,
    value: T = name.get(),
    hint: String = label,
    hidden: Boolean = false,
    noinline builder: Validators<T>.() -> Validator<T>
) = text(name, label, value, hint, hidden) {
    min(2)
    builder()
}