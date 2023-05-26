package symphony.internal

import cinematic.mutableLiveOf
import symphony.Confirmable
import symphony.ConfirmationBox

@PublishedApi
internal class ConfirmableImpl : Confirmable {
    override val confirm = mutableLiveOf<ConfirmationBox?>(null)

    override fun hideConfirmationBox() {
        confirm.value = null
    }
}