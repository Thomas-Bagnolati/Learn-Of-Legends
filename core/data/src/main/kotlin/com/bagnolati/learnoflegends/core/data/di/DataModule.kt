package com.bagnolati.learnoflegends.core.data.di

import com.bagnolati.learnoflegends.core.data.repository.ChampionRepository
import com.bagnolati.learnoflegends.core.data.repository.ChampionRepositoryImpl
import com.bagnolati.learnoflegends.core.data.repository.ItemRepository
import com.bagnolati.learnoflegends.core.data.repository.ItemRepositoryImpl
import com.bagnolati.learnoflegends.core.data.repository.UserDataRepository
import com.bagnolati.learnoflegends.core.data.repository.UserDataRepositoryImpl
import com.bagnolati.learnoflegends.core.data.util.ConnectivityManagerNetworkMonitor
import com.bagnolati.learnoflegends.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository

    @Binds
    fun bindsChampionRepository(
        championRepositoryImpl: ChampionRepositoryImpl
    ): ChampionRepository

    @Binds
    fun bindsItemRepository(
        itemRepositoryImpl: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}
