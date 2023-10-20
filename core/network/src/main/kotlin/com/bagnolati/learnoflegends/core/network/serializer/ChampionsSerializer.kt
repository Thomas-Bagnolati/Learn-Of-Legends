package com.bagnolati.learnoflegends.core.network.serializer

import com.bagnolati.learnoflegends.core.network.model.NetworkChampion
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject


/**
 * Serializer to serialize response as [NetworkChampion] from Ddragon response.
 */
object NetworkChampionsSerializer : KSerializer<List<NetworkChampion>> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("data", kind = PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): List<NetworkChampion> {
        val jsonDecoder = decoder as? JsonDecoder ?: error("Can be deserialized only by JSON")
        val json = jsonDecoder.json
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject

        return jsonObject.map {
            json.decodeFromJsonElement(it.value)
        }
    }

    override fun serialize(encoder: Encoder, value: List<NetworkChampion>) {

    }
}