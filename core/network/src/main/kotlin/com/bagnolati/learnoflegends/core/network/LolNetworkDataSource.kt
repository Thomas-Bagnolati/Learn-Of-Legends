package com.bagnolati.learnoflegends.core.network

import com.bagnolati.learnoflegends.core.network.model.NetworkChampion
import com.bagnolati.learnoflegends.core.network.model.NetworkItem

/**
 * Interface representing network calls to the LOL backend
 */
interface LolNetworkDataSource {
    suspend fun getChampions(): List<NetworkChampion>
    suspend fun getChampion(championId: String): NetworkChampion
    suspend fun getItems(): List<NetworkItem>
}
