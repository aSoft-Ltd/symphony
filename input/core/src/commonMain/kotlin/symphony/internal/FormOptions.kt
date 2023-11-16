package symphony.internal

import lexi.LoggerFactory
import symphony.Fields
import symphony.SubmitActionsBuilder
import symphony.Visibility

internal class FormOptions<out R, out O : Any, out F : Fields<O>>(
    val heading: String,
    val details: String,
    val fields: F,
    val visibility: Visibility,
    val exitOnSuccess: Boolean,
    val logger: LoggerFactory,
    val actions: SubmitActionsBuilder<@UnsafeVariance O, @UnsafeVariance R>
)