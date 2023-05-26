@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.ConfirmableImpl
import kotlin.js.JsName

@JsName("_ignore_Confirmable")
inline fun Confirmable(): Confirmable = ConfirmableImpl()