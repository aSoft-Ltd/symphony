package symphony.validation

fun ValidationResult.throwIfInvalid() {
    if (this is Invalid) throw cause
}