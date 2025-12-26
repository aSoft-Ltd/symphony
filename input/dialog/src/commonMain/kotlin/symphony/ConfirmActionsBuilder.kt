package symphony

import kevlar.Action0
import kevlar.Action0Invoker
import kevlar.action0
import kevlar.builders.Actions0Builder

@Deprecated("In favour of symphony.ConfirmBuilder")
class ConfirmActionsBuilder : Actions0Builder<Unit>() {
    private var _submitAction: Action0Invoker<Any?>? = null
    val submitAction: Action0Invoker<Any?> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onConfirm(name: String = "Confirm", handler: () -> Any?): Action0<Any?> {
        val action = action0(name, handler = handler)
        _submitAction = action
        return action
    }
}