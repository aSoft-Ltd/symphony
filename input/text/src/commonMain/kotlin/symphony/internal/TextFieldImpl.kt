package symphony.internal

import neat.Validator
import neat.Validators
import symphony.TextField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class TextFieldImpl<O : String?>(
    name: KMutableProperty0<O>,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    validator: (Validators<O>.() -> Validator<O>)?
) : AbstractPrimitiveField<O>(name, label, value, hidden, hint, validator), TextField<O>