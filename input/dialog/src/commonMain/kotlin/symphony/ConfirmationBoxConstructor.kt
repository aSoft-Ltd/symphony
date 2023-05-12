package symphony

import symphony.internal.ConfirmationBoxImpl

fun ConfirmationBox(
    heading: String,
    details: String,
    message: String = "Executing, please wait . . .",
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
): ConfirmationBox = ConfirmationBoxImpl(heading, details, message, actionsBuilder)