package symphony

import kotlinx.serialization.KSerializer

interface Serializable<T> {
    val serializer: KSerializer<T>
}