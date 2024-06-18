@file:JsExport
package symphony

import cinematic.Live
import kollections.List
import kotlinx.JsExport

interface StaticSectionRowForm : SectionRow {
    val total: NumberField<Double>
    val children: Live<List<SectionRow>>
}