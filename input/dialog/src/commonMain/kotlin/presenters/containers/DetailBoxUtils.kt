package presenters.containers

@Deprecated("use symphony instead")
fun <T> DetailBox<T>?.toString() = when (this) {
    null -> ""
    else -> "$value"
}