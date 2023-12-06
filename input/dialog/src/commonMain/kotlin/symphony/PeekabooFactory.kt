package symphony

import symphony.internal.PeekabooImpl

fun <T> Peekaboo(): Peekaboo<Any?, T> = PeekabooImpl(null)

fun <P, T> Peekaboo(factory: Peekaboo<P, *>.(P) -> T): Peekaboo<P, T> = PeekabooImpl(factory)