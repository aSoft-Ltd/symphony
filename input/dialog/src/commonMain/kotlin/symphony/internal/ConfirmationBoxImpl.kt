package symphony.internal

import cinematic.BaseScene
import cinematic.MutableLive
import cinematic.mutableLiveOf
import kase.Executing
import kase.ExecutorState
import kase.Failure
import kase.Pending
import kase.Success
import kase.bagOf
import kollections.find
import koncurrent.FailedLater
import koncurrent.later.catch
import koncurrent.later.then
import symphony.ConfirmActionsBuilder
import symphony.ConfirmationBox

@PublishedApi
internal class ConfirmationBoxImpl(
    override val heading: String,
    override val details: String,
    val executionMessage: String,
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
) : BaseScene(), ConfirmationBox {

    private val actions = ConfirmActionsBuilder().apply(actionsBuilder)

    override val state: MutableLive<ExecutorState<Unit>> = mutableLiveOf(Pending, 2)

    private val cancelBag = bagOf(actions.actions.find { it.name.contains("cancel", ignoreCase = true) }?.asInvoker?.handler)

    override fun onCancel(handler: () -> Unit) {
        cancelBag.value = handler
    }

    private val confirmAction = actions.submitAction

    override fun exit() {
        if (state.value is Executing) return
        try {
            cancelBag.value?.invoke()
        } catch (cause: Throwable) {
            state.value = Failure(cause)
        }
    }

    override fun confirm() = try {
        state.value = Executing(message = executionMessage)
        confirmAction()
    } catch (err: Throwable) {
        FailedLater(err)
    }.then {
        state.value = Success(Unit)
    }.catch {
        state.value = Failure(it)
        throw it
    }
}