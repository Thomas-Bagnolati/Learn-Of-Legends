package com.bagnolati.learnoflegends.core.network.model

import com.bagnolati.learnoflegends.core.network.serializer.NetworkChampionsSerializer
import kotlinx.serialization.Serializable


/**
 * Wrapper for data provided from the [LolBaseUrl] when data as list of [NetworkChampion]
 */
@Serializable
data class NetworkChampionsResponse(
    val type: String,
    val format: String,
    val version: String,
    @Serializable(NetworkChampionsSerializer::class)
    val data: List<NetworkChampion>
)