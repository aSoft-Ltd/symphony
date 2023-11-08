@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "OPT_IN_USAGE", "NOTHING_TO_INLINE")

package symphony

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kollections.iEmptyList
import neat.Validity
import neat.aggregate
import symphony.properties.Clearable
import symphony.properties.Finishable
import symphony.properties.Hideable
import symphony.properties.Resetable
import symphony.properties.Validable
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

abstract class Fields<out O : Any>(initial: State<O>) : Validable, Clearable, Finishable, Resetable {
    @JsExport.Ignore
    constructor(output: O) : this(State(output, Feedbacks(iEmptyList())))

    private val all = mutableMapOf<KProperty<*>, Field<*, *>>()

    val output get() = state.value.output

    @JsExport.Ignore
    val state: MutableLive<State<@UnsafeVariance O>> = mutableLiveOf(initial)

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

    fun <F : Field<*, *>> Fields<*>.getOrCreate(
        property: KProperty<Any?>,
        builder: () -> F
    ): F = all.getOrPut(property, builder) as F

    @JsExport.Ignore
    data class State<out O>(
        val output: O,
        val feedbacks: Feedbacks
    )

    @JvmName("inform")
    fun notify() {
        state.value = state.value.copy(output = output)
    }
}