@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.Collection
import kollections.List
import symphony.properties.Settable
import kotlin.js.JsExport

interface SingleChoiceField<O> : Field<PrimitiveFieldState<O?, O>>, Settable<O> {
    val mapper: (O) -> Option

    val items: Collection<O>

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