@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.validation

import cinematic.Live
import symphony.InputFieldState
import symphony.LiveData
import kotlin.js.JsExport

interface Validateable<T> : LiveData<T> {
    val feedback: Live<InputFieldState>
    fun validate(value: T? = data.value.output): ValidationResult
    fun validateSettingInvalidsAsWarnings(value: T? = data.value.output): ValidationResult
    fun validateSettingInvalidsAsErrors(value: T? = data.value.output): ValidationResult
}