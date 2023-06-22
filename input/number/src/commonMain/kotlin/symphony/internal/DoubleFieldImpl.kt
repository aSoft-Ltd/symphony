package symphony.internal

import neat.Validator
import neat.Validators
import symphony.NumberField
import kotlin.reflect.KMutableProperty0

internal class DoubleFieldImpl<N : Double?>(
    name: KMutableProperty0<N>,
    label: String,
    value: N,
    hidden: Boolean,
    hint: String,
    validator: (Validators<N>.() -> Validator<N>)?
) : AbstractBaseField<N>(name, label, value, hidden, hint, validator), NumberField<N> {

    override fun increment(step: N?) {
        val o = output ?: 0.0
        val s = step ?: 1.0
        set((o + s) as N)
    }

    override fun decrement(step: N?) {
        val o = output ?: 0.0
        val s = step ?: 1.0
        set((o + s) as N)
    }

    override fun set(double: Double?) {
        val s = double as? N ?: return
        set(s)
    }

    override fun set(text: String?) {
        val s = text?.toDoubleOrNull() as? N ?: return
        set(s)
    }
}