package symphony.internal

import neat.Validator
import neat.Validators
import symphony.TransformingNumberField
import kotlin.reflect.KMutableProperty0

internal class TransformingDoubleFieldImpl<I : Double?, O>(
    name: KMutableProperty0<O>,
    transformer: (I) -> O,
    label: String,
    value: O,
    hidden: Boolean,
    hint: String,
    validator: (Validators<O>.() -> Validator<O>)?
) : AbstractTransformingField<I, O>(name, transformer, label, value, hidden, hint, validator), TransformingNumberField<I, O> {

    override fun increment(step: I?) {
        val o = input ?: 0.0
        val s = step ?: 1.0
        set((o + s) as I)
    }

    override fun decrement(step: I?) {
        val o = input ?: 0.0
        val s = step ?: 1.0
        set((o + s) as I)
    }

    override fun set(double: Double?) {
        val s = double as? I ?: return
        set(s)
    }

    override fun set(text: String?) {
        val s = text?.toDoubleOrNull() as? I ?: return
        set(s)
    }
}