@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.List
import kotlinx.JsExport

interface TransformingSingleChoiceField<I, O> : TransformingField<I, O>, ChoiceField<I> {

    val selectedItem: O?

    val selectedOption: Option?

    fun options(withSelect: Boolean = false): List<Option>

    fun selectOption(option: Option)

    fun selectLabel(optionLabel: String)

    fun selectValue(optionValue: String)

    fun selectItem(item: I)

    fun select(item: I)

    fun unselect()
}