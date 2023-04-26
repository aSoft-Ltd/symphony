package presenters

import kotlinx.serialization.KSerializer

@Deprecated("use symphony")
interface Serializable<T> {
    val serializer: KSerializer<T>
}