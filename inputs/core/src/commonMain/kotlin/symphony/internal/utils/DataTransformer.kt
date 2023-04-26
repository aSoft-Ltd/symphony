package symphony.internal.utils

import symphony.DataFormatted
import symphony.Formatter
import symphony.internal.FormattedData

class DataTransformer<I : Any, O : Any>(
    val formatter: Formatter<O>?,
    val transformer: (I?) -> O?
) {
    fun toFormattedData(value: I?): DataFormatted<I, O> {
        val o = transformer(value)
        return FormattedData(
            raw = value,
            formatted = formatted(value, o),
            output = o
        )
    }

    protected fun formatted(input: I?, output: O?): String = formatter?.invoke(output) ?: input?.toString() ?: ""
}