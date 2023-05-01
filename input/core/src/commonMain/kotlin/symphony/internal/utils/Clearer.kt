package symphony.internal.utils

import cinematic.MutableLive
import symphony.InputFieldState
import symphony.properties.Clearable

class Clearer<out O>(
    private val value: O,
    private val data: MutableLive<O>,
    private val feedback: MutableLive<InputFieldState>,
) : Clearable {
    override fun clear() {
        data.value = value
        feedback.value = InputFieldState.Empty
    }
}