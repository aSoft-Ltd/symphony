package symphony

import kollections.toIList
import neat.Invalid
import neat.Valid
import neat.Validity

fun Validity<*>.reasons(): List<String> = when (this) {
    is Valid -> emptyList()
    is Invalid -> reasons
}

fun Validity<*>.toWarnings() = reasons().map { Warning(it) }.toIList()

fun Validity<*>.toErrors() = reasons().map { Error(it) }.toIList()