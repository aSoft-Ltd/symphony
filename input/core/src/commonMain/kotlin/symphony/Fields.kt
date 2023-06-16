@file:JsExport
@file:Suppress("WRONG_EXPORTED_DECLARATION", "NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package symphony

import symphony.properties.Clearable
import symphony.validation.Validateable
import symphony.validation.ValidationResult
import kotlin.js.JsExport
import kotlin.reflect.KMutableProperty0

abstract class Fields<out O : Any>(val output: O, @PublishedApi internal val cache: MutableMap<String, InputField> = mutableMapOf()) {


    internal val all get() = cache.values

    /**
     * @return a [List] of invalid [InputField]s
     *
     * Note that if this is list is empty, it is safe to assume that all inputs are valid
     */
    fun validate(): List<Validateable<out Any?>> = all.filterIsInstance<Validateable<out Any?>>().map {
        it.validateSettingInvalidsAsErrors()
        it
    }

    fun clearAll() = all.filterIsInstance<Clearable>().forEach { it.clear() }

    fun stopAllWatchers() {
        clearAll()
        all.filterIsInstance<LiveData<*>>().forEach { it.data.stopAll() }
    }

    inline fun <T : Any> KMutableProperty0<T>.setIfNotNull(value: T?) {
        if (value != null) set(value)
    }
}