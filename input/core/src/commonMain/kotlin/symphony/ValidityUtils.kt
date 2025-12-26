package symphony

import neat.Invalid
import neat.Valid
import neat.Validity

fun Validity<*>.reasons(): List<String> = when (this) {
    is Valid -> emptyList()
    is Invalid -> reasons.toList()
}

fun Validity<*>.toWarnings() = reasons().map { WarningFeedback(it) }

fun Validity<*>.toErrors() = reasons().map { ErrorFeedback(it) }