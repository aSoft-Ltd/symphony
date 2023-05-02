package symphony.internal

import cinematic.BaseScene
import kase.Executing
import kase.ExecutorState
import kase.Failure
import kase.Pending
import kase.Success
import koncurrent.FailedLater
import cinematic.MutableLive
import cinematic.mutableLiveOf
import kevlar.mutableAction0
import lexi.Logable
import symphony.ConfirmActionsBuilder
import symphony.ConfirmationBox

@PublishedApi
internal class ConfirmationBoxImpl(
    override val heading: String,
    override val details: String,
    val executionMessage: String,
    config: Logable,
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
) : BaseScene(), ConfirmationBox {

    private val actions = ConfirmActionsBuilder().apply(actionsBuilder)

    override val state: MutableLive<ExecutorState<Unit>> = mutableLiveOf(Pending, 2)

    override val cancel = mutableAction0("Cancel") {
        val action = actions.actions.firstOrNull {
            it.name.contentEquals("cancel", ignoreCase = true)
        }?.handler ?: {
            config.logger.warn("Cancel hasn't been handled yet")
        }
        action()
    }

    private val confirmAction = actions.submitAction

    override fun exit() {
        try {
            cancel()
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