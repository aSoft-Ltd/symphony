@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.MutableLive
import cinematic.mutableLiveOf
import neat.Validity
import neat.aggregate
import symphony.properties.Clearable
import symphony.properties.Resetable
import symphony.properties.Validable
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

abstract class Fields<out O>(val output: O) {

    private val all = mutableMapOf<KProperty<*>, Field<*>>()

    val state: MutableLive<@UnsafeVariance O> = mutableLiveOf(output)

    fun validate(): Validity<O> = all.values.filterIsInstance<Validable>().map {
        it.validateToErrors()
    }.aggregate(output)

    fun clear() = all.values.filterIsInstance<Clearable>().map { it.clear() }

    fun finish() {
        all.values.forEach { it.finish() }
        state.stopAll()
    }

    fun reset() = all.values.filterIsInstance<Resetable>().forEach { it.reset() }

    @JsName("_ignore_setAndUpdateNoNullable")
    @JvmName("_ignore_setAndUpdateNoNullable")
    inline fun <T : Any> KMutableProperty0<T>.setAndUpdate(value: T?) {
        if (value != null) set(value)
        state.dispatch(output)
    }

    @JsName("_ignore_setAndUpdatenNullable")
    @JvmName("_ignore_setAndUpdatenNullable")
    inline fun <T : Any> KMutableProperty0<T?>.setAndUpdate(value: T?) {
        set(value)
        state.dispatch(output)
    }

    fun <F : Field<*>> Fields<*>.getOrCreate(
        property: KProperty<Any?>,
        builder: () -> F
    ): F = all.getOrPut(property, builder) as F
}