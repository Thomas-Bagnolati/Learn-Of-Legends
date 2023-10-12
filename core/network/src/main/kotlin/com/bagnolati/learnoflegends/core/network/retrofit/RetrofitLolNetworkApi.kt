package com.bagnolati.learnoflegends.core.network.retrofit

import com.bagnolati.learnoflegends.core.network.model.NetworkChampionsResponse
import com.bagnolati.learnoflegends.core.network.model.NetworkItemsResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API declaration for Lol Network API
 */
interface RetrofitLolNetworkApi {

    @GET("data/en_US/champion.json")
    suspend fun getChampions(
    ): NetworkChampionsResponse

    @GET("data/en_US/champion/{championId}.json")
    suspend fun getChampion(
        @Path("championId") championId: String
    ): NetworkChampionsResponse

    @GET("data/en_US/item.json")
    suspend fun getItems(
    ): NetworkItemsResponse

}