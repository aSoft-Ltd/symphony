@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.mutableLiveOf
import epsilon.FileBlob
import kotlin.js.JsExport

class ImageUploaderScene {
    val state = mutableLiveOf<State>(PendingState)

    sealed interface State
    object PendingState : State
    data class RefiningState(val image: FileBlob) : State

    fun select(image: FileBlob) {
        state.value = RefiningState(image)
    }
}