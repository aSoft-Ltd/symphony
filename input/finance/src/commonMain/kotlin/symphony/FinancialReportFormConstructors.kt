package symphony

import symphony.internal.BalanceSheetFormImpl
import symphony.internal.CashFlowStatementFormImpl
import symphony.internal.IncomeStatementFormImpl
import kotlin.js.JsName

@JsName("balanceSheetForm")
fun BalanceSheetForm(): BalanceSheetForm = BalanceSheetFormImpl()

@JsName("cashFlowStatementForm")
fun CashFlowStatementForm(): CashFlowStatementForm = CashFlowStatementFormImpl()

@JsName("incomeStatementForm")
fun IncomeStatementForm(): IncomeStatementForm = IncomeStatementFormImpl()