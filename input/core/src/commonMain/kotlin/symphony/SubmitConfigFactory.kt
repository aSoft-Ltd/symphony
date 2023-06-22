@file:Suppress("NOTHING_TO_INLINE")

package symphony

import lexi.ConsoleAppender
import lexi.Logable
import lexi.Logger
import symphony.internal.SubmitConfigImpl

inline fun SubmitConfig(
    logger: Logger = Logger(ConsoleAppender()),
    exitOnSuccess: Boolean = SubmitConfigImpl.exitOnSuccess
): SubmitConfig = SubmitConfigImpl(logger, exitOnSuccess)

inline fun Logable.toSubmitConfig(
    logger: Logger = this.logger,
    exitOnSuccess: Boolean = SubmitConfigImpl.exitOnSuccess
) = SubmitConfig(logger, exitOnSuccess)

@Deprecated(
    message = "In favour of toSubmitConfig",
    replaceWith = ReplaceWith("toSubmitConfig()", "symphony.toSubmitConfig")
)
fun Logable.toFormConfig(
    logger: Logger = this.logger,
    exitOnSuccess: Boolean = SubmitConfigImpl.exitOnSuccess
) = SubmitConfig(logger, exitOnSuccess)