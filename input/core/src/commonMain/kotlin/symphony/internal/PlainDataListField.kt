package symphony.internal

import kollections.Collection
import kollections.List
import cinematic.MutableLive
import cinematic.mutableLiveOf
import symphony.CommonInputProperties
import symphony.Data
import symphony.LiveDataList
import symphony.internal.utils.Clearer
import symphony.internal.utils.OutputSetter
import symphony.properties.Settable

abstract class PlainDataListField<O>(value: Collection<O>?) : CompoundValidateableInputField<List<O>>(), LiveDataList<O>, Settable<List<O>>, CommonInputProperties {
    protected val default = OutputList(value)
    override val data = mutableLiveOf(default)

    protected val setter by lazy { OutputSetter(data as MutableLive<Data<List<O>>>, feedback, cv) }
    override fun set(value: List<O>?) = setter.set(value)

    private val clearer by lazy { Clearer(default, data as MutableLive<Data<List<O>>>, feedback) }
    override fun clear() = clearer.clear()
}