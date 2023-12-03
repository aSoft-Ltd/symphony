package symphony

import kevlar.Action0
import symphony.internal.ConfirmImpl

fun <P> Confirm(factory: ConfirmBuilder.(P) -> Action0<Any?>): Confirm<P> = ConfirmImpl(factory)