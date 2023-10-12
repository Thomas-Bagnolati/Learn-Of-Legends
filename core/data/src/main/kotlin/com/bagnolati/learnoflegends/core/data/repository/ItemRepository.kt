package com.bagnolati.learnoflegends.core.data.repository

import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.network.model.NetworkItem

interface ItemRepository {
    /**
     * Get all League of legends items
     */
    suspend fun getItems(): Result<List<NetworkItem>>
}