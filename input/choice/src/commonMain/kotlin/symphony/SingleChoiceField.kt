@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import symphony.properties.Settable
import kotlinx.JsExport

interface SingleChoiceField<O> : Field<O, SingleChoiceFieldState<O>>, BaseFieldState<O>, Settable<O>, ChoiceField<O>, Searchable {

    val selectedItem: O?

    val selectedOption: Option?

    fun options(withSelect: Boolean = false): List<Option>

    fun selectOption(option: Option)

    fun selectLabel(optionLabel: String)

    fun selectValue(optionValue: String)

    fun selectItem(item: O)

    fun select(item: O)

    fun unselect()
}