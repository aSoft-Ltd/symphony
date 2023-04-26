package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import symphony.CommonInputProperties
import symphony.DataFormatted
import symphony.LiveDataFormatted
import symphony.internal.utils.Clearer
import symphony.internal.utils.DataTransformer
import symphony.internal.utils.FormattedOutputSetter
import symphony.properties.Settable

abstract class TransformedDataField<I : Any, O : Any>(value: O?) : CompoundValidateableInputField<O>(), LiveDataFormatted<I, O>, Settable<I>, CommonInputProperties {
    protected val default = FormattedData<I, O>(null, "", value)
    override val data: MutableLive<DataFormatted<I, O>> = mutableLiveOf(default)

    abstract val transformer: DataTransformer<I, O>

    protected val setter by lazy { FormattedOutputSetter(data, feedback, transformer, cv) }
    override fun set(value: I?) = setter.set(value)

    private val clearer by lazy { Clearer(default, data, feedback) }
    override fun clear() = clearer.clear()
}