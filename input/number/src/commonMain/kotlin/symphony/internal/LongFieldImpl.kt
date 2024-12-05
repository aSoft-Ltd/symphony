package symphony.internal

import neat.ValidationFactory
import symphony.Changer
import symphony.Visibility
import kotlin.math.roundToLong
import kotlin.reflect.KMutableProperty0

internal class LongFieldImpl(
    backer: FieldBacker<Long>,
    value: Long?,
    label: String,
    visibility: Visibility,
    hint: String,
    onChange: Changer<Long>? = null,
    factory: ValidationFactory<Long>?
) : NumberFieldImpl<Long>(backer, value, label, visibility, hint, onChange, factory) {

    override fun set(value: Long?) = setProhibiting(value)

    override fun increment(step: Long?) {
        val o = output ?: 0L
        val s = step ?: 1L
        set(o + s)
    }

    override fun decrement(step: Long?) {
        val o = output ?: 0L
        val s = step ?: 1L
        set(o - s)
    }

    override fun set(double: Double?) = set(double?.roundToLong())

    override fun set(text: String?) = set(text?.toLongOrNull())
}