package symphony.internal

import neat.ValidationFactory
import symphony.TextField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class TextFieldImpl<O : String?>(
    name: KMutableProperty0<O>,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    factory: ValidationFactory<O>?
) : AbstractPrimitiveField<O>(name, label, value, hidden, hint, factory), TextField<O>