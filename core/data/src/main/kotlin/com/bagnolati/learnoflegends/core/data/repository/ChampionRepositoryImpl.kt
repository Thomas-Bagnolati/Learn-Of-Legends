package com.bagnolati.learnoflegends.core.data.repository

import com.bagnolati.learnoflegends.core.common.network.Dispatcher
import com.bagnolati.learnoflegends.core.common.network.LolDispatchers
import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.network.LolNetworkDataSource
import com.bagnolati.learnoflegends.core.network.model.asChampion
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChampionRepositoryImpl @Inject constructor(
    private val network: LolNetworkDataSource,
    @Dispatcher(LolDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ChampionRepository {

    override suspend fun getChampions(): Result<List<Champion>> = withContext(ioDispatcher) {
        try {
            Result.Success(network.getChampions().map { it.asChampion() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getChampion(championId: String): Result<Champion> = withContext(ioDispatcher) {
        try {
            Result.Success(network.getChampion(championId).asChampion())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}