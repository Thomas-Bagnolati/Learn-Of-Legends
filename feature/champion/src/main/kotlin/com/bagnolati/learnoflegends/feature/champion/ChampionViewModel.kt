package com.bagnolati.learnoflegends.feature.champion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.common.result.Result.Error
import com.bagnolati.learnoflegends.core.common.result.Result.Loading
import com.bagnolati.learnoflegends.core.common.result.Result.Success
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.feature.champion.navigation.ChampionArgs
import com.bagnolati.nutrigood.core.domain.GetChampionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getChampionUseCase: GetChampionUseCase
) : ViewModel() {

    private val championIdArgs: ChampionArgs = ChampionArgs(savedStateHandle)

    private val championResult = MutableStateFlow<Result<Champion>>(Result.Loading)

    val championUiState: StateFlow<ChampionUiState> =
        championResult
            .map { result ->
                when (result) {
                    is Success -> ChampionUiState.Success(result.data)
                    is Error -> ChampionUiState.Error(result.exception)
                    Loading -> ChampionUiState.Loading
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ChampionUiState.Loading
            )

    init {
        fetchChampion()
    }

    fun fetchChampion() {
        viewModelScope.launch {
            championResult.update {
                getChampionUseCase(championIdArgs.championId)
            }
        }
    }
}

interface ChampionUiState {
    object Loading : ChampionUiState
    data class Success(val champion: Champion) : ChampionUiState
    data class Error(val error: Throwable?) : ChampionUiState
}
