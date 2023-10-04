package com.bagnolati.learnoflegends.core.network.retrofit

import com.bagnolati.learnoflegends.core.network.DdragonUrl
import com.bagnolati.learnoflegends.core.network.LolNetworkDataSource
import com.bagnolati.learnoflegends.core.network.model.NetworkChampion
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * [Retrofit] backed [LolNetworkDataSource]
 */
@Singleton
class RetrofitLolNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : LolNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(DdragonUrl.BASE_URL_WITH_VERSION)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitLolNetworkApi::class.java)


    override suspend fun getChampions(): List<NetworkChampion> =
        networkApi.getChampions().data

    override suspend fun getChampion(championId: String): NetworkChampion =
        networkApi.getChampion(championId).data.first()
}

