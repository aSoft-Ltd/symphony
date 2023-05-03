@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import lexi.Logable
import kotlin.js.JsExport

interface FormConfig<out P> : Logable {
    val serializer: KSerializer<@UnsafeVariance P>
    val codec: StringFormat
    val exitOnSubmitted: Boolean
}