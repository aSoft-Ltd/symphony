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
import koncurrent.FailedLater
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

    private val cancelBag = bagOf<() -> Unit>()

    override fun onCancel(handler: () -> Unit) {
        cancelBag.value = handler
    }

    private val confirmAction = actions.submitAction

    override fun exit() {
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