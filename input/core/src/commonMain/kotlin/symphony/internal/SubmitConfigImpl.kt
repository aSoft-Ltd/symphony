package symphony.internal

import lexi.ConsoleAppender
import lexi.Logger
import symphony.SubmitConfig

@PublishedApi
internal open class SubmitConfigImpl(
    override val logger: Logger,
    override val exitOnSuccess: Boolean
) : SubmitConfig {
    companion object DEFAULT : SubmitConfigImpl(
        logger = Logger(ConsoleAppender()),
        exitOnSuccess = true,
    )
}