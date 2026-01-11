@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.JsExport
import symphony.properties.Settable

interface MultiChoiceField<O> : Field<List<O>, MultiChoiceFieldState<O>>, BaseFieldState<List<O>>, Settable<List<O>>, ChoiceField<O>, Searchable {

    val optionLabels: List<String>

    val optionValues: List<String>

    val selectedValues: Set<String>

    val selectedItems: List<O>

    val selectedOptions: List<Option>

    val options: List<Option>

    val optionsWithSelectLabel: List<Option>

    // checkers
    fun isSelected(item: O): Boolean

    fun isSelectedValue(v: String): Boolean

    fun isSelectedOption(o: Option): Boolean

    fun isSelectedLabel(l: String): Boolean

    // adders
    fun addSelectedItem(item: O)

    fun addSelectedOption(o: Option)

    fun addSelectedValue(v: String)

    fun addSelectLabel(l: String)

    // removers
    fun unselectOption(o: Option)

    fun unselectItem(i: O)

    fun unselectValue(v: String)

    fun unselectLabel(l: String)

    fun unselectAll()

    // toggles
    fun toggleSelectedValue(v: String)

    fun toggleSelectedOption(o: Option)

    fun toggleSelectedItem(i: O)

    fun toggleSelectedLabel(l: String)
}