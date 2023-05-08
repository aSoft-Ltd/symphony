@file:Suppress("WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import lexi.ConsoleAppender
import lexi.Logable
import lexi.Logger
import symphony.internal.forms.FormConfigImpl
import symphony.internal.forms.FormConfigImpl.DEFAULT

inline fun <reified P> FormConfig(
    serializer: KSerializer<P> = serializer(),
    logger: Logger = Logger(ConsoleAppender()),
    codec: StringFormat = DEFAULT.codec,
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig<P> = FormConfigImpl(serializer, logger, codec, exitOnSubmitted)

@Deprecated("do fix this to get in a configured codec please, Defaults are killing us")
inline fun <reified P> Logable.toFormConfig(
    serializer: KSerializer<P> = serializer(),
    logger: Logger = this.logger,
    codec: StringFormat = DEFAULT.codec,
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig<P> = FormConfigImpl(serializer, logger, codec, exitOnSubmitted)