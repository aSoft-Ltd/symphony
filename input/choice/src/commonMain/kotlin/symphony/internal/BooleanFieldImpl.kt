package symphony.internal

import neat.ValidationFactory
import symphony.BooleanField
import symphony.Visibility
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class BooleanFieldImpl(
    name: KMutableProperty0<Boolean?>,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<Boolean>? = null,
    factory: ValidationFactory<Boolean>?
) : BaseFieldImpl<Boolean>(name, label, visibility, hint, onChange, factory), BooleanField {

    override fun toggle() = set(output?.not() ?: true)
}