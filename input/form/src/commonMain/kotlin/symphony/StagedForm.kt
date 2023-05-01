@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Scene
import kase.FormState
import kase.Pending
import kotlin.js.JsExport

open class StagedForm<out P, out R>(
    open val heading: String,
    open val details: String,
    open val config: FormConfig<@UnsafeVariance P>,
    initializer: FormActionsBuildingBlock<P, R>,
) : Scene<FormState<R>>(Pending)