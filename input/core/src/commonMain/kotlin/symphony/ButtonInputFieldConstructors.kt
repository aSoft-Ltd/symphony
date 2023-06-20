package symphony

fun Fields<*>.button(
    label: String,
    name: String = label,
) = ButtonInputField(name, Label(label, false))