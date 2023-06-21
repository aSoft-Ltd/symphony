@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kash.Cents
import kash.Currency
import kash.MoneyFormatter
import neat.required
import kotlin.js.JsExport

class MoneyFields(
    currency: Currency,
    cents: Cents,
    formatter: MoneyFormatter
) : Fields<MoneyOutput>(MoneyOutput(currency)) {
    val currency = selectSingle(
        name = output::currency,
        items = Country.values().toList(),
        transformer = { it.currency },
        mapper = { Option(label = it.currency.details, value = it.currency.name) }
    ) { required() }

    val amount = double(name = output::amount)
}