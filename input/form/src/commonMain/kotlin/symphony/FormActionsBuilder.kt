package symphony

import kevlar.Action1Invoker
import kevlar.action1
import kevlar.builders.Actions0Builder
import koncurrent.Later

class FormActionsBuilder<P, R> : Actions0Builder<Unit>() {
    private var _submitAction: Action1Invoker<P, Later<R>>? = null
    val submitAction: Action1Invoker<P, Later<R>> get() = _submitAction ?: error("Submit action has not been initialize just yet")
    fun onSubmit(name: String = "Submit", handler: (P) -> Later<R>): Finalizer {
        val action = action1(name, handler = handler)
        _submitAction = action
        return Finalizer
    }

    internal var onSuccess: (() -> Unit)? = null
    fun onSuccess(cleanup: () -> Unit): Finalizer {
        onSuccess = cleanup
        return Finalizer
    }

    object Finalizer
}