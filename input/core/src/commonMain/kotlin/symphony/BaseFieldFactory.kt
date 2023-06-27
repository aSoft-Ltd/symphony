package symphony

import neat.ValidationFactory
import symphony.internal.BaseFieldImpl
import symphony.internal.Changer
import kotlin.reflect.KMutableProperty0

fun <O> BaseField(
    name: KMutableProperty0<O?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = label,
    onChange: Changer<O>? = null,
    factory: ValidationFactory<O>? = null
): BaseField<O> = BaseFieldImpl(name, label, visibility, hint, onChange, factory)

fun <O> Fields<*>.field(
    name: KMutableProperty0<O?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = label,
    onChange: Changer<O>? = null,
    factory: ValidationFactory<O>? = null
): BaseField<O> = getOrCreate(name) {
    BaseField(name, label, visibility, hint, onChange, factory)
}