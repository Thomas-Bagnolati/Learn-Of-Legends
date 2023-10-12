package com.bagnolati.learnoflegends.core.network.model


import com.bagnolati.learnoflegends.core.model.Champion
import kotlinx.serialization.Serializable


/**
 * Network representation of [Champion]
 */
@Serializable
data class NetworkChampion(
    val version: String? = null,
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val blurb: String,
    val info: NetworkInfo,
    val image: NetworkImage,
    val tags: List<String> = emptyList(),
    val partype: String,
    val stats: NetworkStats,
    val lore: String = "",
    val allytips: List<String> = emptyList(),
    val enemytips: List<String> = emptyList(),
    val passive: NetworkPassive? = null,
    val spells: List<NetworkSpell> = emptyList(),
    val skins : List<NetworkSkin> = emptyList()
) {

    @Serializable
    data class NetworkImage(
        val full: String,
        val sprite: String,
        val group: String,
        val x: Int,
        val y: Int,
        val w: Int,
        val h: Int
    )

    @Serializable
    data class NetworkInfo(
        val attack: Int,
        val defense: Int,
        val magic: Int,
        val difficulty: Int
    )

    @Serializable
    data class NetworkStats(
        val hp: Int,
        val hpperlevel: Int,
        val mp: Double,
        val mpperlevel: Double,
        val movespeed: Int,
        val armor: Int,
        val armorperlevel: Double,
        val spellblock: Int,
        val spellblockperlevel: Double,
        val attackrange: Int,
        val hpregen: Double,
        val hpregenperlevel: Double,
        val mpregen: Double,
        val mpregenperlevel: Double,
        val crit: Int,
        val critperlevel: Int,
        val attackdamage: Int,
        val attackdamageperlevel: Double,
        val attackspeedperlevel: Double,
        val attackspeed: Double
    )

    @Serializable
    data class NetworkPassive(
        val name: String,
        val description: String,
        val image: NetworkImage
    )

    @Serializable
    data class NetworkSpell(
        val id: String,
        val name: String,
        val description: String,
        val tooltip: String,
        val leveltip: NetworkLeveltip? = null,
        val maxrank: Int,
        val cooldown: List<Float>,
        val cooldownBurn: String,
        val cost: List<Int>,
        val costBurn: String,
        val effect: List<List<Float>?>,
        val effectBurn: List<String?>,
        val costType: String,
        val maxammo: String,
        val range: List<Int>,
        val rangeBurn: String?,
        val image: NetworkImage,
        val resource: String
    ) {
        @Serializable
        data class NetworkLeveltip(
            val label: List<String>,
            val effect: List<String>
        )
    }

    @Serializable
    data class NetworkSkin(
        val id: String,
        val num: Int,
        val name: String,
        val chromas: Boolean
    )

}
