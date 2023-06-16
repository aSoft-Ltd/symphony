@file:Suppress("WRONG_EXPORTED_DECLARATION", "NOTHING_TO_INLINE")

package symphony

import lexi.ConsoleAppender
import lexi.Logable
import lexi.Logger
import symphony.internal.forms.FormConfigImpl
import symphony.internal.forms.FormConfigImpl.DEFAULT

inline fun FormConfig(
    logger: Logger = Logger(ConsoleAppender()),
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig = FormConfigImpl(logger, exitOnSubmitted)

inline fun Logable.toFormConfig(
    logger: Logger = this.logger,
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig = FormConfigImpl(logger, exitOnSubmitted)