package symphony

import epsilon.FileBlob
import kollections.List
import kollections.MutableList
import neat.ValidationFactory
import symphony.internal.BaseFieldImpl
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

fun Fields<*>.files(
    name: KProperty0<MutableList<FileBlob>>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<List<FileBlob>>? = null,
    factory: ValidationFactory<List<FileBlob>>? = null
): ListField<FileBlob> = list(name, label, visibility, onChange, factory)

fun Fields<*>.file(
    name: KMutableProperty0<FileBlob?>,
    label: String = name.name,
    hint: String = label,
    visibility: Visibility = Visibility.Visible,
    onChange: Changer<FileBlob>? = null,
    factory: ValidationFactory<FileBlob>? = null
): BaseField<FileBlob> = getOrCreate(name) {
    BaseFieldImpl(name, label, visibility, hint, onChange, factory)
}