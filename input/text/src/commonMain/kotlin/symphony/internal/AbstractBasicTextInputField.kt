package symphony.internal

import symphony.BasicTextInputField
import symphony.internal.utils.Typer

abstract class AbstractBasicTextInputField(value: String?) : PlainDataField<String>(value), BasicTextInputField {
    final override fun type(text: String) = Typer(data.value.output ?: "", setter).type(text)
}