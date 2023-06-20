package symphony

import kotlin.reflect.KMutableProperty0

fun <O> PrimitiveFieldState(
    name: KMutableProperty0<O>,
    label: String,
    required: Boolean,
    hint: String,
    value: O,
    hidden: Boolean
): PrimitiveFieldState<O?, O> = PrimitiveFieldState(
    name = name.name,
    label = Label(label, required),
    hint = hint,
    input = value,
    output = value,
    hidden = hidden,
    required = required,
    feedbacks = Feedbacks(kollections.iEmptyList())
)