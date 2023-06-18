package neat.internal

import neat.Valid
import neat.Validator

@PublishedApi
internal class VoidValidator<T>(override val label: String) : Validator<T> {
    override fun validate(value: T) = Valid(value)
}