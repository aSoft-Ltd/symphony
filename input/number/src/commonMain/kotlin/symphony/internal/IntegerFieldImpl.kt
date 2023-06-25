package symphony.internal

import neat.ValidationFactory
import symphony.NumberField
import kotlin.math.roundToInt
import kotlin.reflect.KMutableProperty0

internal class IntegerFieldImpl<N : Int?>(
    name: KMutableProperty0<N>,
    label: String,
    value: N,
    hidden: Boolean,
    hint: String,
    onChange: Changer<N>? = null,
    factory: ValidationFactory<N>?
) : AbstractBaseField<N>(name, label, value, hidden, hint, onChange, factory), NumberField<N> {
    override fun increment(step: N?) {
        val o = output ?: 0
        val s = step ?: 1
        set((o + s) as N)
    }

    override fun decrement(step: N?) {
        val o = output ?: 0
        val s = step ?: 1
        set((o + s) as N)
    }

    override fun set(double: Double?) = set((double?.roundToInt() ?: 0) as N)

    override fun set(text: String?) = set((text?.toIntOrNull() ?: 0) as N)
}