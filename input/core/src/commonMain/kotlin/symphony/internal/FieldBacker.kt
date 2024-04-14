package symphony.internal

import kotlin.reflect.KMutableProperty0

sealed interface FieldBacker<out O> {

    val name: String

    val asProp: KMutableProperty0<@UnsafeVariance O?>? get() = (this as? Prop)?.value

    class Name(val value: String) : FieldBacker<Nothing> {
        override val name by lazy { value }
    }

    class Prop<O>(val value: KMutableProperty0<O?>) : FieldBacker<O> {
        override val name by lazy { value.name }
    }

    companion object {
        fun <O> from(value: KMutableProperty0<O>) = Prop(value as KMutableProperty0<O?>)

        fun from(value: String) = Name(value)
    }
}