package neat

import kollections.iListOf
import neat.internal.VoidValidator

fun string(label: String = "unnamed"): Validator<String> = VoidValidator(label)

fun Validator<String>.length(
    value: Int,
    message: (value: String) -> String = { "$label should have $value character(s), but $it has ${it.length} character(s) instead" }
) = chained { if (it.length == value) Valid(it) else Invalid(it, iListOf(message(it))) }

fun Validator<String>.min(
    value: Int,
    message: (String) -> String = { "$label should have a more than $value character(s), but $it has ${it.length} character(s) instead" }
) = chained { if (it.length >= value) Valid(it) else Invalid(it, iListOf(message(it))) }

fun Validator<String>.max(
    value: Int,
    message: (String) -> String = { "$label should have less than $value character(s), but $it has ${it.length} character(s) instead" }
) = chained { if (it.length <= value) Valid(it) else Invalid(it, iListOf(message(it))) }

fun Validator<String>.notBlank(
    message: (String) -> String = { "$label is required to not be empty but it was" }
) = chained { if (it.isNotBlank()) Valid(it) else Invalid(it, iListOf(message(it))) }
