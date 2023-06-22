@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import symphony.properties.Clearable
import symphony.properties.Finishable
import symphony.properties.Hideable
import symphony.properties.Resetable
import kotlin.js.JsExport

//open class MultiStageForm<P : Any, out F : Fields<@UnsafeVariance P>, out I : Input<F>>(
//    open val heading: String,
//    open val details: String,
//    open val inputs: List<I>
//) : Resetable, Clearable, Finishable, Hideable {
//
//    private val label = "MultiStageForm"
//}