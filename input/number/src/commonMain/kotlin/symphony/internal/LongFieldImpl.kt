package symphony.internal

import neat.ValidationFactory
import symphony.NumberField
import kotlin.math.roundToLong
import kotlin.reflect.KMutableProperty0

internal class LongFieldImpl<N : Long?>(
    name: KMutableProperty0<N>,
    label: String,
    value: N,
    hidden: Boolean,
    hint: String,
    onChange: Changer<N>? = null,
    factory: ValidationFactory<N>?
) : AbstractBaseField<N>(name, label, value, hidden, hint, onChange, factory), NumberField<N> {
    override fun increment(step: N?) {
        val o = output ?: 0L
        val s = step ?: 1L
        set((o + s) as N)
    }

    override fun decrement(step: N?) {
        val o = output ?: 0L
        val s = step ?: 1L
        set((o + s) as N)
    }

    override fun set(double: Double?) {
        val s = double?.roundToLong() as? N ?: return
        set(s)
    }

    override fun set(text: String?) {
        val s = text?.toLongOrNull() as? N ?: return
        set(s)
    }
}