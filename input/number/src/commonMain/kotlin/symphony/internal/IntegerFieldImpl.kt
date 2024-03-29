package symphony.internal

import neat.ValidationFactory
import symphony.Changer
import symphony.Visibility
import kotlin.math.roundToInt
import kotlin.reflect.KMutableProperty0

internal class IntegerFieldImpl(
    backer: FieldBacker<Int>,
    value: Int?,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<Int>? = null,
    factory: ValidationFactory<Int>?
) : NumberFieldImpl<Int>(backer, value, label, visibility, hint, onChange, factory) {

    override fun set(value: Int?) = setProhibiting(value)

    override fun increment(step: Int?) {
        val o = output ?: 0
        val s = step ?: 1
        set(o + s)
    }

    override fun decrement(step: Int?) {
        val o = output ?: 0
        val s = step ?: 1
        set(o + s)
    }

    override fun set(double: Double?) = set(double?.roundToInt())

    override fun set(text: String?) = set(text?.toIntOrNull())
}