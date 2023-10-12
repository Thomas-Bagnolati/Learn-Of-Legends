package com.bagnolati.nutrigood.core.domain

import com.bagnolati.learnoflegends.core.common.result.Result
import com.bagnolati.learnoflegends.core.common.result.mapOnSuccess
import com.bagnolati.learnoflegends.core.data.repository.ItemRepository
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.nutrigood.core.domain.mapper.asItem
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(): Result<List<Item>> =
        itemRepository.getItems().mapOnSuccess { networkItem ->
            networkItem.map { it.asItem() }
        }
}