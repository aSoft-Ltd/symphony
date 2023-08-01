@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.List
import symphony.properties.Settable
import kotlin.js.JsExport

interface SingleChoiceField<O> : Field<O, SingleChoiceFieldState<O>>, BaseFieldState<O>, Settable<O>, ChoiceField<O> {

    val selectedItem: O?

    val selectedOption: Option?

    fun searchByFiltering(key: String?)

    fun searchByOrdering(key: String?)

    fun clearSearch()

    fun options(withSelect: Boolean = false): List<Option>

    fun selectOption(option: Option)

    fun selectLabel(optionLabel: String)

    fun selectValue(optionValue: String)

    fun selectItem(item: O)

    fun select(item: O)

    fun unselect()
}