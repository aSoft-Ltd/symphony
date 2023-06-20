@file:Suppress("NOTHING_TO_INLINE")

package symphony

inline fun <T : Enum<T>> T?.toOption(
    label: String = this?.name ?: "null"
) = Option(label, this?.name ?: "null")