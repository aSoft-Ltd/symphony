package symphony.internal.utils

import kollections.toIList
import cinematic.MutableLive
import symphony.Data
import symphony.InputFieldState
import symphony.internal.OutputData
import symphony.internal.OutputList
import symphony.properties.Settable
import symphony.validation.Validateable

class OutputSetter<in V>(
    private val data: MutableLive<Data<V>>,
    private val feedback: MutableLive<InputFieldState>,
    private val validator: Validateable<V>
) : FeedbackSetter(feedback), Settable<V> {
    override fun set(value: V?) {
        data.value = when (value) {
            is Collection<Any?> -> OutputList(value.toIList()) as Data<V>
            else -> OutputData(value)
        }
        setFeedbacksAsWarnings(validator.validate(value))
    }
}