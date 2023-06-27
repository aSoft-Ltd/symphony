package symphony.internal

import neat.ValidationFactory
import symphony.NumberField
import symphony.Visibility
import kotlin.reflect.KMutableProperty0

internal abstract class NumberFieldImpl<N : Number>(
    property: KMutableProperty0<N?>,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<N>? = null,
    factory: ValidationFactory<N>?
) : BaseFieldImpl<N>(property, label, visibility, hint, onChange, factory), NumberField<N> {

    protected fun setProhibiting(value: N?) {
        if (value == null && output != null) return
        super.set(value)
    }
}