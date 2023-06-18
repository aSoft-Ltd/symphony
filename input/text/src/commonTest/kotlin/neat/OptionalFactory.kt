package neat

import neat.internal.OptionalValidator

fun <T : Any> Validator<T>.optional(): Validator<T?> = OptionalValidator(label, this)