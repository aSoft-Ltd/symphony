package presenters

import kotlin.reflect.KProperty

@Deprecated("use symphony")
inline fun <F : InputField> Fields.getOrCreate(
    property: KProperty<Any?>,
    builder: () -> F
): F = getOrCreate(property.name, builder)

@Deprecated("use symphony")
inline fun <F : InputField> Fields.getOrCreate(
    name: String,
    builder: () -> F
): F = cache.getOrPut(name) { builder() } as F