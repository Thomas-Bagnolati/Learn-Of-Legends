package com.bagnolati.learnoflegends.feature.champions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder
import com.bagnolati.learnoflegends.feature.champions.component.getStatByOrderAsDouble
import com.bagnolati.nutrigood.core.domain.GetChampionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionsViewModel @Inject constructor(
    private val getChampionsUseCase: GetChampionsUseCase
) : ViewModel() {

    private val championsResponse = MutableStateFlow<Result<List<Champion>>>(Result.Loading)
    private val selectedChampion = MutableStateFlow<Champion?>(null)
    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val championsOrder = MutableStateFlow(ChampionOrder.ALPHABETIC)

    val championsUiState: StateFlow<ChampionsUiState> =
        championUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ChampionsUiState.Loading
            )

    init {
        fetchChampions()
    }

    fun orderChampions(order: ChampionOrder) {
        championsOrder.update { order }
    }

    fun searchChampion(query: String) {
        searchQuery.update { query }
    }

    fun selectChampion(champion: Champion) {
        selectedChampion.update { champion }
    }

    fun onSubmitKeyboard() {
        if (championsUiState.value is ChampionsUiState.Success) {
            (championsUiState.value as ChampionsUiState.Success).champions.firstOrNull()?.let { firstChampionOnList ->
                selectedChampion.update { firstChampionOnList }
                searchQuery.update { "" }
            }
        }
    }

    fun onCloseSearchRow() {
        searchQuery.update { "" }
    }

    fun fetchChampions() {
        viewModelScope.launch {
            championsResponse.update { Result.Loading }
            championsResponse.update { getChampionsUseCase() }
        }
    }

    private fun championUiState(): Flow<ChampionsUiState> {
        return combine(
            championsOrder,
            searchQuery,
            championsResponse,
            selectedChampion,
        ) { selectedOrder: ChampionOrder, searchQuery: String, result: Result<List<Champion>>, selectedChampion: Champion? ->

            when (result) {
                is Result.Success -> {

                    val championsFiltered = result.data
                        .orderChampions(selectedOrder)
                        .filter {
                            it.name.lowercase().trim()
                                .contains(searchQuery.lowercase().trim())
                        }

                    val indexOfSelectedChampion =
                        championsFiltered.indexOf(selectedChampion).let { index ->
                            if (index == -1) null
                            else index
                        }

                    val allStatsByOrder = result.data.map {
                        it.getStatByOrderAsDouble(selectedOrder)
                    }

                    ChampionsUiState.Success(
                        champions = championsFiltered,
                        selectedChampionOrder = selectedOrder,
                        selectedChampion = selectedChampion,
                        indexOfSelectedChampion = indexOfSelectedChampion,
                        minStatValue = allStatsByOrder.min(),
                        maxStatValue = allStatsByOrder.max(),
                        searchQuery = searchQuery
                    )
                }

                is Result.Loading -> ChampionsUiState.Loading
                is Result.Error -> ChampionsUiState.Error(error = result.exception)
            }
        }
    }

    /**
     * Order a list of [Champion] by [ChampionOrder].
     *
     * @return new List of [Champion] ordered.
     */
    private fun List<Champion>.orderChampions(order: ChampionOrder): List<Champion> {
        return this
            // By String
            .sortedBy {
                if (order == ChampionOrder.ALPHABETIC) it.name
                else null
            }
            // By Int
            .sortedByDescending {
                when (order) {
                    ChampionOrder.HP -> it.stats.hp
                    ChampionOrder.HP_LVL -> it.stats.hpPerLevel
                    ChampionOrder.MOVE_SPEED -> it.stats.moveSpeed
                    ChampionOrder.ARMOR -> it.stats.armor
                    ChampionOrder.MAGIC_RESIST -> it.stats.spellBlock
                    ChampionOrder.ATTACK_RANGE -> it.stats.attackRange
                    ChampionOrder.CRIT -> it.stats.crit
                    ChampionOrder.CRIT_LVL -> it.stats.critPerLevel
                    ChampionOrder.ATTACK_DAMAGE -> it.stats.attackDamage
                    else -> null
                }
            }
            // By Double
            .sortedByDescending {
                when (order) {
                    ChampionOrder.MP -> it.stats.mp
                    ChampionOrder.MP_LVL -> it.stats.mpPerLevel
                    ChampionOrder.ARMOR_LVL -> it.stats.armorPerLevel
                    ChampionOrder.MAGIC_RESIST_LVL -> it.stats.spellBlockPerLevel
                    ChampionOrder.HP_REGEN -> it.stats.hpRegen
                    ChampionOrder.HP_REGEN_LVL -> it.stats.hpRegenPerLevel
                    ChampionOrder.MP_REGEN -> it.stats.mpRegen
                    ChampionOrder.MP_REGEN_LVL -> it.stats.mpRegenPerLevel
                    ChampionOrder.ATTACK_DAMAGE_LVL -> it.stats.attackDamagePerLevel
                    ChampionOrder.ATTACK_SPEED -> it.stats.attackSpeed
                    ChampionOrder.ATTACK_SPEED_LVL -> it.stats.attackSpeedPerLevel
                    else -> null
                }
            }
    }

}


interface ChampionsUiState {
    data object Loading : ChampionsUiState
    data class Success(
        val champions: List<Champion>,
        val selectedChampionOrder: ChampionOrder,
        val minStatValue: Double,
        val maxStatValue: Double,
        val selectedChampion: Champion?,
        val indexOfSelectedChampion: Int?,
        val searchQuery: String,
    ) : ChampionsUiState

    data class Error(val error: Throwable?) : ChampionsUiState
}