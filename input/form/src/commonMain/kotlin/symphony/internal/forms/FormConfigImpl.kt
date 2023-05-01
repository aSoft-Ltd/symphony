package symphony.internal.forms

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.serializer
import lexi.ConsoleAppender
import lexi.Logger
import symphony.FormConfig
import viewmodel.VIEW_MODEL_CONFIG_DEFAULT

open class FormConfigImpl<P>(
    override val serializer: KSerializer<P>,
    override val logger: Logger,
    override val codec: StringFormat,
    override val exitOnSubmitted: Boolean
) : FormConfig<P> {
    companion object DEFAULT : FormConfigImpl<Unit>(
        serializer = Unit.serializer(),
        logger = Logger(ConsoleAppender()),
        codec = VIEW_MODEL_CONFIG_DEFAULT.codec,
        exitOnSubmitted = true,
    )
}