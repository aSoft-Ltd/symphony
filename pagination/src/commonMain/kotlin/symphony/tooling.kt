package symphony

fun Map<String, Any?>.toQueryString(): String = entries.joinToString("&") { "${it.key}=${it.value}" }