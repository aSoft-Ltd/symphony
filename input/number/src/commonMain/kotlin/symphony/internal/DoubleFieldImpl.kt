package symphony.internal

import neat.ValidationFactory
import symphony.Changer
import symphony.Visibility
import kotlin.reflect.KMutableProperty0

internal class DoubleFieldImpl(
    name: KMutableProperty0<Double?>,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<Double>? = null,
    factory: ValidationFactory<Double>?
) : NumberFieldImpl<Double>(name, label, visibility, hint, onChange, factory) {

    override fun set(double: Double?) = setProhibiting(double)

    override fun increment(step: Double?) {
        val o = output ?: 0.0
        val s = step ?: 1.0
        set(o + s)
    }

    override fun decrement(step: Double?) {
        val o = output ?: 0.0
        val s = step ?: 1.0
        set(o + s)
    }

    override fun set(text: String?) = set(text?.toDoubleOrNull() ?: 0.0)
}