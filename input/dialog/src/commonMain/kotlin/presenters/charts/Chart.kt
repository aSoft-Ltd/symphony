@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.charts

import kotlinx.JsExport
import kotlinx.serialization.Serializable

@Deprecated("use symphony instead")
@Serializable
data class Chart<out D>(
    val title: String,
    val description: String,
    val labels: List<String>,
    val datasets: List<DataSet<D>>,
) {
    @Serializable
    data class DataSet<out D>(
        val name: String,
        val values: List<D>
    )

    fun <R> map(transform: (D) -> R): Chart<R> = Chart(
        title = title,
        description = description,
        labels = labels,
        datasets = datasets.map {
            DataSet(it.name, it.values.map(transform))
        }
    )
}