@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport
import kotlin.reflect.KProperty

data class FormState<out O, out R>(
    val visibility: Visibility,
    val phase: FormPhase<O, R>
) {
    val isSubmitting get() = phase is SubmittingPhase
}

sealed interface FormPhase<out O, out R> {
    val asCapturing get() = this as? CapturingPhase
    val asValidating get() = this as? ValidatingPhase
    val asSubmitting get() = this as? SubmittingPhase
    val asSuccess get() = this as? SuccessPhase
    val asFailure get() = this as? FailurePhase
}

data object CapturingPhase : FormPhase<Nothing, Nothing>

data class ValidatingPhase<out O>(
    val output: O
) : FormPhase<O, Nothing>

data class SubmittingPhase<out O>(
    val output: O
) : FormPhase<O, Nothing>

data class SuccessPhase<out O, out R>(
    val output: O,
    val result: R
) : FormPhase<O, R>

data class FailurePhase<out O>(
    val output: O,
    val reasons: List<String>,
    val fields: List<FieldError>
) : FormPhase<O, Nothing> {

    data class FieldError(
        val name: KProperty<*>,
        val error: List<String>
    ) {
        override fun toString() = "FieldError(name=${name.name}, error=$error)"
    }
}