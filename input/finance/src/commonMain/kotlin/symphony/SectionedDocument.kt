@file:JsExport
package symphony

import cinematic.Live
import kollections.List
import kotlinx.JsExport

interface SectionedDocument {
    val rows: Live<List<SectionRow>>
}