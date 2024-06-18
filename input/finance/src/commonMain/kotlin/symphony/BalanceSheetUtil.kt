package symphony

import symphony.internal.BalanceSheetFormImpl
import kotlin.js.JsName

@JsName("balanceSheetFormOf")
fun BalanceSheetForm(): BalanceSheetForm = BalanceSheetFormImpl()