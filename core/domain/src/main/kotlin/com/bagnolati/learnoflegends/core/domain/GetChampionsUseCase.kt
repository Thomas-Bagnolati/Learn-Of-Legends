package com.bagnolati.learnoflegends.core.domain

import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.common.result.mapOnSuccess
import com.bagnolati.learnoflegends.core.data.repository.ChampionRepository
import com.bagnolati.learnoflegends.core.domain.mapper.asChampions
import com.bagnolati.learnoflegends.core.model.Champion
import javax.inject.Inject

class GetChampionsUseCase @Inject constructor(
    private val championRepository: ChampionRepository
) {
    suspend operator fun invoke(): Result<List<Champion>> =
        championRepository.getChampions()
            .mapOnSuccess { it.asChampions() }
}