package com.bagnolati.learnoflegends.core.network.model

import com.bagnolati.learnoflegends.core.network.serializer.NetworkChampionsSerializer
import com.bagnolati.learnoflegends.core.network.serializer.NetworkItemsSerializer
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

/**
 * Wrapper for data provided from the [LolBaseUrl] when data as list of [NetworkItem]
 */
@Serializable
data class NetworkItemsResponse(
    val type: String,
    val version: String,
    @Serializable(NetworkItemsSerializer::class)
    val data: List<NetworkItem>
)
