package neat.internal

import kollections.toIList
import neat.Invalid
import neat.Valid
import neat.Validator
import neat.Validity

@PublishedApi
internal class ChainedValidator<T>(
    override val label: String,
    private val anchor: Validator<T>,
    private val validator: (T) -> Validity<T>
) : Validator<T> {
    override fun validate(value: T): Validity<T> {
        val parent = anchor.validate(value)
        val child = validator(value)
        return when (parent) {
            is Valid -> child
            is Invalid -> when (child) {
                is Valid -> Invalid(value, parent.reasons)
                is Invalid -> Invalid(value, (parent.reasons + child.reasons).toIList())
            }
        }
    }
}