@file:Suppress("NOTHING_TO_INLINE")

package neat

import kollections.iListOf
import neat.internal.ChainedValidator

inline fun <T> Validator<T>.chained(
    noinline validator: (T) -> Validity<T>
): Validator<T> = ChainedValidator(label, this, validator)

fun <T> Validator<T>.required(
    message: (T) -> String = { "$label is required, but was null" }
) = chained { if (it != null) Valid(it) else Invalid(it, iListOf(message(it))) }