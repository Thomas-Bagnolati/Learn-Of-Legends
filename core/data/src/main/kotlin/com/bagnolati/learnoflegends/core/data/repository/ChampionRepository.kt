package com.bagnolati.learnoflegends.core.data.repository

import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.network.model.NetworkChampion

interface ChampionRepository {
    /**
     * Get all League of legends champions
     */
    suspend fun getChampions(): Result<List<NetworkChampion>>

    /**
     * Get champion by id
     */
    suspend fun getChampion(championId: String): Result<NetworkChampion>

}