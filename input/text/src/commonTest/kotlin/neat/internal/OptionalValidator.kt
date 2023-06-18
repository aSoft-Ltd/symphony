package neat.internal

import neat.Valid
import neat.Validator
import neat.Validity

@PublishedApi
internal class OptionalValidator<T : Any>(
    override val label: String,
    private val wrapped: Validator<T>
) : Validator<T?> {
    override fun validate(value: T?): Validity<T?> = when (value) {
        null -> Valid(value)
        else -> wrapped.validate(value)
    }
}