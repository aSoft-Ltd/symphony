package symphony.internal.text

import kotlinx.serialization.builtins.serializer
import symphony.BasicTextInputField
import symphony.internal.PlainDataField
import symphony.internal.utils.Typer

abstract class AbstractBasicTextInputField(value: String?) : PlainDataField<String>(value), BasicTextInputField {
    final override val serializer = String.serializer()
    final override fun type(text: String) = Typer(data.value.output ?: "", setter).type(text)
}