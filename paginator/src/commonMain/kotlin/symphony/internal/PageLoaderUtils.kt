package symphony.internal

import koncurrent.FailedLater

internal fun unInitializedError() = FailedLater("You are using an uninitialized page loader")

internal fun deInitializedError() = FailedLater("You are using an de initialized page loader")