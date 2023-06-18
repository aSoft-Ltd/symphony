package neat

import kollections.List

sealed interface Validity<out T> {
    val value: T
}

data class Valid<out T>(
    override val value: T
) : Validity<T>

data class Invalid<out T>(
    override val value: T,
    val reasons: List<String>
) : Validity<T>