package com.bagnolati.learnoflegends.feature.champions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.domain.GetChampionsUseCase
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.feature.champions.component.ChampionOrder
import com.bagnolati.learnoflegends.feature.champions.component.getStatByOrder
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
                        .sortedBy { it.getStatByOrder(selectedOrder) }
                        .filter {
                            it.name.lowercase().trim()
                                .contains(searchQuery.lowercase().trim())
                        }

                    val indexOfSelectedChampion =
                        championsFiltered.indexOf(selectedChampion).let { index ->
                            if (index == -1) null
                            else index
                        }

                    val statsByOrder = result.data.map {
                        it.getStatByOrder(selectedOrder)
                    }

                    ChampionsUiState.Success(
                        champions = championsFiltered,
                        selectedChampionOrder = selectedOrder,
                        selectedChampion = selectedChampion,
                        indexOfSelectedChampion = indexOfSelectedChampion,
                        minStatOfOrder = statsByOrder.min(),
                        maxStatOfOrder = statsByOrder.max(),
                        searchQuery = searchQuery
                    )
                }

                is Result.Loading -> ChampionsUiState.Loading
                is Result.Error -> ChampionsUiState.Error(error = result.exception)
            }
        }
    }

}


interface ChampionsUiState {
    data object Loading : ChampionsUiState
    data class Success(
        val champions: List<Champion>,
        val selectedChampionOrder: ChampionOrder,
        val minStatOfOrder: Double,
        val maxStatOfOrder: Double,
        val selectedChampion: Champion?,
        val indexOfSelectedChampion: Int?,
        val searchQuery: String,
    ) : ChampionsUiState

    data class Error(val error: Throwable?) : ChampionsUiState
}