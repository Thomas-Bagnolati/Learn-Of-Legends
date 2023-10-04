package com.bagnolati.learnoflegends.core.network

import com.bagnolati.learnoflegends.core.network.model.NetworkChampion

/**
 * Interface representing network calls to the LOL backend
 */
interface LolNetworkDataSource {
    suspend fun getChampions(): List<NetworkChampion>
    suspend fun getChampion(championId: String): NetworkChampion
}
