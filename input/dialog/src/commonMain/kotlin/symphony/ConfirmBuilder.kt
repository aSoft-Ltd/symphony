package symphony

import kase.Pending
import kase.bagOf
import kevlar.Action0
import kevlar.Action0Invoker
import kevlar.action0
import kevlar.builders.Actions0Builder
import koncurrent.Later
import symphony.internal.VisibleConfirmStateImpl

class ConfirmBuilder : Actions0Builder<Unit>() {
    var heading: String = "No Heading"
    var details: String = "No Details"
    var message: String = "Executing, please wait . . ."

    internal var confirm: Action0Invoker<Later<Any?>>? = null
    fun onConfirm(name: String = "Confirm", handler: () -> Later<Any?>): Action0<Any?> {
        val action = action0(name, handler = handler)
        confirm = action
        return action
    }

    internal var cancel: Action0Invoker<Unit>? = null
    fun onCancel(name: String = "Cancel", handler: () -> Unit): Action0<Unit> {
        val action = action0(name, handler = handler)
        cancel = action
        return action
    }

    fun noConfirmAction(): Nothing = error("Confirm action has not been initialize just yet")

    internal val cancelBag by lazy {
        bagOf(actions.find { it.name.contains("cancel", ignoreCase = true) }?.asInvoker?.handler)
    }

    internal fun <S> toState(subject: S) = VisibleConfirmStateImpl(
        heading = heading,
        details = details,
        message = message,
        subject = subject,
        phase = Pending
    )
}