package symphony

inline fun <T : Enum<T>> T.filter(
    value: String = this.name,
    key: String
) = value.startsWith(key, ignoreCase = true)