package symphony.validation

internal fun ValidationResult.throwIfInvalid() {
    if (this is Invalid) throw cause
}