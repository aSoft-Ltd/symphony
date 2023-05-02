@file:Suppress("WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import lexi.Logable
import lexi.Logger
import symphony.internal.forms.FormConfigImpl
import symphony.internal.forms.FormConfigImpl.DEFAULT

inline fun <reified P> FormConfig(
    serializer: KSerializer<P> = serializer(),
    logger: Logger = DEFAULT.logger,
    codec: StringFormat = DEFAULT.codec,
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig<P> = FormConfigImpl(serializer, logger, codec, exitOnSubmitted)

inline fun <reified P> Logable.toFormConfig(
    serializer: KSerializer<P> = serializer(),
    logger: Logger = this.logger,
    codec: StringFormat = DEFAULT.codec,
    exitOnSubmitted: Boolean = DEFAULT.exitOnSubmitted
): FormConfig<P> = FormConfigImpl(serializer, logger, codec, exitOnSubmitted)