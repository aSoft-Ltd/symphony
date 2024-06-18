@file:JsExport

package symphony

import cinematic.Live
import kollections.List
import kotlinx.JsExport

interface DynamicRowNode : DynamicRowContent {

    val amount: Live<Double>

    val branches: Live<List<DynamicRowContent>>

    fun remove(branch: DynamicRowContent?)

    fun add()
}