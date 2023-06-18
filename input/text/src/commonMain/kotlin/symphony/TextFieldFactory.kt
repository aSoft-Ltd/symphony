package symphony

import symphony.internal.TextFieldImpl

fun TextField(
    name: String,
    label: String = name,
    value: String? = null,
    required: Boolean = true,
    hidden: Boolean = false,
    hint: String = label,
): TextField = TextFieldImpl(
    name = name,
    label = Label(label, required),
    value = value ?: "",
    hidden = hidden,
    required = required,
    hint = hint
)