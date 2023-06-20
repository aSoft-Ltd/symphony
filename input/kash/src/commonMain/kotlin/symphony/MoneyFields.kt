@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kash.Cents
import kash.Currency
import kash.MoneyFormatter
import kash.MoneyPresenter
import kash.transformers.toPresenter
import kotlin.js.JsExport

class MoneyFields(
    currency: Currency,
    cents: Cents,
    formatter: MoneyFormatter
) : Fields<MoneyPresenter>(cents.toPresenter(currency, formatter)) {
    val currency = selectSingle(
        name = output::currency,
        items = Country.values().toList(),
        transformer = { it.currency },
        mapper = { Option(label = it.currency.details, value = it.currency.name) }
    )
}