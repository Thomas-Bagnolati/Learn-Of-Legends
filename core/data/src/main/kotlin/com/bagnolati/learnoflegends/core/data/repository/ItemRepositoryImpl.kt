package com.bagnolati.learnoflegends.core.data.repository

import com.bagnolati.learnoflegends.core.common.network.Dispatcher
import com.bagnolati.learnoflegends.core.common.network.LolDispatchers
import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.network.LolNetworkDataSource
import com.bagnolati.learnoflegends.core.network.model.NetworkItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val network: LolNetworkDataSource,
    @Dispatcher(LolDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ItemRepository {
    override suspend fun getItems(): Result<List<NetworkItem>> = withContext(ioDispatcher) {
        try {
            Result.Success(network.getItems())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}