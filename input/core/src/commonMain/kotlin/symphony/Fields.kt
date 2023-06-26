@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kollections.iEmptyList
import neat.Validity
import neat.aggregate
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

abstract class Fields<out O : Any>(initial: State<O>) : Field<Fields.State<O>> {
    @JsName("_ignore_fields")
    constructor(output: O) : this(State(false, output, Feedbacks(iEmptyList())))

    private val all = mutableMapOf<KProperty<*>, Field<*>>()

    val output get() = state.value.output

    override val state: MutableLive<State<@UnsafeVariance O>> = mutableLiveOf(initial)

    override fun validate(): Validity<O> = all.values.map {
        it.validateToErrors()
    }.aggregate(output)

    override fun clear() {
        all.values.map { it.clear() }
    }

    override fun validateToErrors() = when (val res = validate()) {
        is neat.Valid -> res
        is neat.Invalid -> {
            state.value = state.value.copy(feedbacks = Feedbacks(res.toErrors()))
            res
        }
    }

    override fun finish() {
        all.values.forEach { it.finish() }
        state.stopAll()
    }

    override fun reset() = all.values.forEach { it.reset() }

    override val hidden: Boolean get() = state.value.hidden

    override fun show(show: Boolean?) {
        state.value = state.value.copy(hidden = show != true)
    }

    override fun hide(hide: Boolean?) {
        state.value = state.value.copy(hidden = hide == true)
    }

    @JsName("_ignore_setAndUpdateNoNullable")
    @JvmName("_ignore_setAndUpdateNoNullable")
    inline fun <T : Any> KMutableProperty0<T>.setAndUpdate(value: T?) {
        if (value != null) set(value)
        notify()
    }

    @JsName("_ignore_setAndUpdateNullable")
    @JvmName("_ignore_setAndUpdateNullable")
    inline fun <T : Any> KMutableProperty0<T?>.setAndUpdate(value: T?) {
        set(value)
        notify()
    }

    fun <F : Field<*>> Fields<*>.getOrCreate(
        property: KProperty<Any?>,
        builder: () -> F
    ): F = all.getOrPut(property, builder) as F

    data class State<out O>(
        val hidden: Boolean,
        val output: O,
        val feedbacks: Feedbacks
    )

    fun notify() {
        state.value = state.value.copy(output = output)
    }
}