package symphony.internal

import neat.ValidationFactory
import symphony.Changer
import symphony.NumberField
import symphony.Visibility
import kotlin.reflect.KMutableProperty0

internal abstract class NumberFieldImpl<N : Number>(
    backer: FieldBacker<N>,
    value: N?,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<N>? = null,
    factory: ValidationFactory<N>?
) : GenericBaseField<N>(backer, value, label, visibility, hint, onChange, factory), NumberField<N> {

    protected fun setProhibiting(value: N?) {
        if (value == null && output != null) return
        super.set(value)
    }
}