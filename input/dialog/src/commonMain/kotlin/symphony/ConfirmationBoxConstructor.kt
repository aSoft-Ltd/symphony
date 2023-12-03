package symphony

import symphony.internal.ConfirmationBoxImpl

@Deprecated("In favour of symphony.Confirm")
fun ConfirmationBox(
    heading: String,
    details: String,
    message: String = "Executing, please wait . . .",
    actionsBuilder: ConfirmActionsBuilder.() -> Unit
): ConfirmationBox = ConfirmationBoxImpl(heading, details, message, actionsBuilder)