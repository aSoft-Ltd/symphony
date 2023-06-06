package symphony

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PhoneNumber::class)
object PhoneSerializer : KSerializer<PhoneNumber> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("symphony.PhoneNumber")

    override fun serialize(encoder: Encoder, value: PhoneNumber) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): PhoneNumber {
        throw UnsupportedOperationException("PhoneNumber deserialization has not yet been implemented")
    }
}