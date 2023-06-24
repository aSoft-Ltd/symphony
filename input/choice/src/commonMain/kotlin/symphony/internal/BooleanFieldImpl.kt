package symphony.internal

import neat.ValidationFactory
import symphony.BooleanField
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class BooleanFieldImpl<B : Boolean?>(
    name: KMutableProperty0<B>,
    label: String,
    value: B,
    hidden: Boolean,
    hint: String,
    onChange: Changer<B>? = null,
    factory: ValidationFactory<B>?
) : AbstractBaseField<B>(name, label, value, hidden, hint, onChange, factory), BooleanField<B> {

    override fun toggle() = set((output?.not() ?: false) as B)
}