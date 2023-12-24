package symphony

import kevlar.Action1Invoker
import kevlar.action1
import kevlar.builders.Actions0Builder
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import neat.ValidationFactory

class SubmitActionsBuilder<P, R> : Actions0Builder<Unit>() {
    private var _submitAction: Action1Invoker<P, Later<R>>? = null
    val submitAction: Action1Invoker<P, Later<R>> get() = _submitAction ?: error("Submit action has not been initialize just yet")

    var factory: ValidationFactory<P>? = null

    fun validate(factory: ValidationFactory<P>) {
        this.factory = factory
    }

    fun onSubmit(name: String = "Submit", handler: (P) -> Later<R>): Finalizer {
        val action = action1(name, handler = handler)
        _submitAction = action
        return Finalizer
    }

    internal var onSuccess: ((R) -> Unit)? = null
    fun onSuccess(handler: (R) -> Unit): Finalizer {
        onSuccess = handler
        return Finalizer
    }

    internal var onFailure: ((Throwable) -> Unit)? = null
    fun onFailure(handler: (Throwable) -> Unit): Finalizer {
        onFailure = handler
        return Finalizer
    }

    object Finalizer
}