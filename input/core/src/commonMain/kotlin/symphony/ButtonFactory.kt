package symphony

fun Fields<*>.button(
    label: String,
    name: String = label,
) = Button(name, Label(label, false))