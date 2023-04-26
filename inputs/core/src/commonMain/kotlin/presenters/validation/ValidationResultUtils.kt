package presenters.validation

@Deprecated("use symphony")
internal fun ValidationResult.throwIfInvalid() {
    if (this is Invalid) throw cause
}