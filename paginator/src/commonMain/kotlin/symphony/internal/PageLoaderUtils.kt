package symphony.internal

internal fun unInitializedError() = IllegalStateException("You are using an uninitialized page loader")

internal fun deInitializedError() = IllegalStateException("You are using an de initialized page loader")