package symphony

import kevlar.Action0
import kevlar.action0
import kevlar.builders.Actions0Builder
import koncurrent.Later

class ConfirmActionsBuilder : Actions0Builder<Unit>() {
    private var _submitAction: Action0<Later<Any?>>? = null
    val submitAction: Action0<Later<Any?>> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onConfirm(name: String = "Confirm", handler: () -> Later<Any?>): Action0<Any?> {
        val action = action0(name, handler = handler)
        _submitAction = action
        return action
    }
}