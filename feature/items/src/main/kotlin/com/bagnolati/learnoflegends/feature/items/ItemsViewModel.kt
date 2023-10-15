package com.bagnolati.learnoflegends.feature.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.nutrigood.core.domain.GetItemsUseCase
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
class ItemsViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val itemsResponse = MutableStateFlow<Result<List<Item>>>(Result.Loading)
    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val itemSelected: MutableStateFlow<Item?> = MutableStateFlow(null)
    private val sort: MutableStateFlow<ItemsSort> = MutableStateFlow(ItemsSort.DEFAULT)

    val itemsUiState: StateFlow<ItemsUiState> =
        itemsUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ItemsUiState.Loading
            )

    init {
        fetchItems()
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.update { query }
    }

    fun fetchItems() {
        viewModelScope.launch {
            itemsResponse.update { Result.Loading }
            itemsResponse.update { getItemsUseCase() }
        }
    }

    fun selectSort(itemsSort: ItemsSort) {
        sort.update { itemsSort }
    }

    fun selectItem(item: Item) {
        itemSelected.update { item }
    }

    private fun itemsUiState(): Flow<ItemsUiState> {
        return combine(
            itemsResponse,
            searchQuery,
            sort,
            itemSelected
        ) { result: Result<List<Item>>, query: String, sort: ItemsSort, itemSelected: Item? ->
            when (result) {
                is Result.Success -> {
                    val filteredItems = result.data
                        .filter { it.maps.summonersRift }
                        .filter { it.getStatBySortItem(sort) > 0 }
                        .sortedBy { it.getStatBySortItem(sort) }
                        .filter {
                            it.name.lowercase()
                                .contains(query.lowercase())
                        }


                    ItemsUiState.Success(
                        items = filteredItems,
                        searchQuery = query,
                        sort = sort,
                        selectedItem = itemSelected
                    )
                }

                Result.Loading -> ItemsUiState.Loading
                is Result.Error -> ItemsUiState.Error(result.exception)
            }
        }
    }


}


interface ItemsUiState {
    object Loading : ItemsUiState
    data class Success(
        val items: List<Item>,
        val selectedItem: Item?,
        val searchQuery: String,
        val sort: ItemsSort
    ) : ItemsUiState

    data class Error(val exception: Throwable?) : ItemsUiState
}