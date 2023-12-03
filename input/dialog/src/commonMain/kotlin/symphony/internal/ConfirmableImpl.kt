package symphony.internal

import cinematic.mutableLiveOf
import symphony.Confirmable
import symphony.ConfirmationBox

@PublishedApi
@Deprecated("In favour of symphony.Confirm")
internal class ConfirmableImpl : Confirmable {
    override val confirm = mutableLiveOf<ConfirmationBox?>(null)

    override fun hideConfirmationBox() {
        confirm.value = null
    }
}