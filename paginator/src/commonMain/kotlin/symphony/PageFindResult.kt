@file:JsExport
package symphony

import kotlinx.JsExport

interface PageFindResult<out T> {
    val page: Page
    val row: Row<T>
}