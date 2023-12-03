@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.ConfirmableImpl
import kotlin.js.JsName

@JsName("_ignore_Confirmable")
@Deprecated("In favour of symphony.Confirm")
inline fun Confirmable(): Confirmable = ConfirmableImpl()