package symphony

import neat.ValidationFactory
import neat.min
import neat.notBlank
import symphony.internal.Changer
import kotlin.reflect.KMutableProperty0

fun TextField(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = BaseField(name, label, visibility, hint, onChange, factory)

fun Fields<*>.text(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = getOrCreate(name) { TextField(name, label, visibility, hint, onChange, factory) }

fun Fields<*>.name(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = text(name, label, visibility, hint, onChange) {
    min(2)
    notBlank()
    configure(factory)
}

fun Fields<*>.password(
    name: KMutableProperty0<String?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = label,
    onChange: Changer<String>? = null,
    factory: ValidationFactory<String>? = null
) = text(name, label, visibility, hint, onChange, factory)