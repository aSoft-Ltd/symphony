package symphony.internal

import neat.Validator
import neat.Validators
import symphony.BooleanField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class BooleanFieldImpl<O : Boolean?>(
    name: KMutableProperty0<O>,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    validator: (Validators<O>.() -> Validator<O>)?
) : AbstractBaseField<O>(name,label,value,hidden,hint,validator), BooleanField<O> {

    override fun toggle() = set((output?.not() ?: false) as O)
}