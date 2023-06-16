package symphony.internal.forms

import lexi.ConsoleAppender
import lexi.Logger
import symphony.FormConfig

@PublishedApi
internal open class FormConfigImpl(
    override val logger: Logger,
    override val exitOnSubmitted: Boolean
) : FormConfig {
    companion object DEFAULT : FormConfigImpl(
        logger = Logger(ConsoleAppender()),
        exitOnSubmitted = true,
    )
}