package symphony.internal

import neat.ValidationFactory
import symphony.BooleanField
import symphony.Changer
import symphony.Visibility
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class BooleanFieldImpl(
    backer: FieldBacker<Boolean>,
    value: Boolean?,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<Boolean>? = null,
    factory: ValidationFactory<Boolean>?
) : GenericBaseField<Boolean>(backer, value, label, visibility, hint, onChange, factory), BooleanField {

    override fun toggle() = set(output?.not() ?: true)
}