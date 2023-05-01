@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.serialization.KSerializer
import kotlin.js.JsExport
import kotlinx.serialization.StringFormat
import lexi.Logger

interface FormConfig<P> {
    val logger: Logger
    val serializer: KSerializer<P>
    val codec: StringFormat
    val exitOnSubmitted: Boolean
}