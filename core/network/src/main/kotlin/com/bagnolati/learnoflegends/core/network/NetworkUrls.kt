package com.bagnolati.learnoflegends.core.network


/**
 * Ddragon URL.
 *
 * See [documentation](https://developer.riotgames.com/docs/lol)
 */
internal object DdragonUrl {
    private const val VERSION = "13.18.1"
//    private const val BASE_URL = BuildConfig.DDRAGON_BASE_URL
    private const val BASE_URL = "https://ddragon.leagueoflegends.com/"
    const val BASE_URL_WITH_VERSION = "${BASE_URL}cdn/$VERSION/"

    private const val VERSIONS_URL = "${BASE_URL}api/versions.json"

    const val PASSIVE_IMAGE_URL = "${BASE_URL_WITH_VERSION}img/passive/"
    const val MINI_IMAGE_URL = "${BASE_URL_WITH_VERSION}img/champion/"
    const val SPLASH_IMAGE_URL = "${BASE_URL}cdn/img/champion/splash/"
    const val LOADING_IMAGE_URL = "${BASE_URL}img/champion/loading/"
    const val SPELL_IMAGE_URL = "${BASE_URL_WITH_VERSION}img/spell/"
}

internal object LeagueOfLegendsUrl {
    private const val BASE_URL = "https://universe.leagueoflegends.com/"
//    private const val BASE_URL = BuildConfig.LOL_BASE_URL
    const val ROLE_IMG = "${BASE_URL}images/"
}