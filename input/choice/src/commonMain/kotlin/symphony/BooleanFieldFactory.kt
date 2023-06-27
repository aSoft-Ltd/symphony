package symphony

import neat.ValidationFactory
import symphony.internal.BooleanFieldImpl
import symphony.internal.Changer
import kotlin.reflect.KMutableProperty0

fun Fields<*>.boolean(
    name: KMutableProperty0<Boolean?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<Boolean>? = null,
    factory: ValidationFactory<Boolean>? = null
): BooleanField = getOrCreate(name) {
    BooleanFieldImpl(name, label, visibility, hint, onChange, factory)
}