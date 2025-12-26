package symphony.internal

import cinematic.mutableLiveOf
import kase.Executing
import kase.ExecutorState
import kase.Failure
import kevlar.Action0
import symphony.Confirm
import symphony.ConfirmBuilder
import symphony.ConfirmState
import symphony.FormAction
import symphony.FormActions
import symphony.HiddenConfirmState
import symphony.Label
import symphony.VisibleConfirmState

@PublishedApi
internal class ConfirmImpl<P>(private val factory: ConfirmBuilder.(P) -> Action0<Any?>) : Confirm<P> {

    override val state by lazy { mutableLiveOf<ConfirmState<P>>(HiddenConfirmState) }

    override val actions: FormActions
        get() {
            return FormActions(
                cancel = FormAction(Label(acts.cancel?.name ?: "Cancel", false)) { hide() },
                submit = FormAction(Label(acts.confirm?.name ?: "Confirm", false)) { confirm() }
            )
        }

    private var acts = ConfirmBuilder()

    private var recursionDetector = 0

    override fun hide() {
        if (state.value.isWorking) return
        try {
            if (recursionDetector > 0) {
                throw UnsupportedOperationException("Do not call confirm.cancel() in its onCancel block, the confirm modal already calls itself")
            }
            recursionDetector++
            acts.cancelBag.value?.invoke()
        } finally {
            state.value = HiddenConfirmState
            recursionDetector = 0
        }
    }

    override fun show(params: P) {
        acts = ConfirmBuilder().apply { factory(params) }
        state.value = acts.toState(params)
    }

    override fun confirm() {
        val s = state.value.data ?: return TODO() // FailedLater("Confirming an invisible confirm is not allowed")
        if (state.value.isWorking) return TODO() // FailedLater("Can't confirm while confirmation is busy")
        TODO("Finish this confirmation properly")
//        return try {
//            state.value = s.copy(phase = Executing(message = s.message))
//            acts.confirm?.invoke() ?: acts.noConfirmAction()
//        } catch (err: Throwable) {
//            FailedLater(err)
//        }.then {
//            state.value = HiddenConfirmState
//        }.catch {
//            state.value = s.copy(phase = Failure(it))
//            throw it
//        }
    }

    private fun <S> VisibleConfirmState<S>.copy(phase: ExecutorState<Unit>) = VisibleConfirmStateImpl(heading, details, message, subject, phase)
}