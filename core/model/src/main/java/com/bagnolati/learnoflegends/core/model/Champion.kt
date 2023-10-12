package com.bagnolati.learnoflegends.core.model


/**
 * Data representation of a Lol Champion.
 */
data class Champion(
    val id: String,
    val name: String,
    val title: String,
    val blurbTrunked: String = "",
    val blurbComplete: String = "",
    val imageUrl: ImageUrl,
    val stats: Stats,
    val tags: List<Tag> = emptyList(),
    val allyTips: List<String> = emptyList(),
    val enemyTips: List<String> = emptyList(),
    val passive: Passive? = null,
    val spells: List<Spell> = emptyList(),
    val skins: List<Skin> = emptyList()
) {
    data class Stats(
        val alphabetic: Double,
        val hp: Double,
        val hpPerLevel: Double,
        val mp: Double,
        val mpPerLevel: Double,
        val moveSpeed: Double,
        val armor: Double,
        val armorPerLevel: Double,
        val spellBlock: Double,
        val spellBlockPerLevel: Double,
        val attackRange: Double,
        val hpRegen: Double,
        val hpRegenPerLevel: Double,
        val mpRegen: Double,
        val mpRegenPerLevel: Double,
        val crit: Double,
        val critPerLevel: Double,
        val attackDamage: Double,
        val attackDamagePerLevel: Double,
        val attackSpeedPerLevel: Double,
        val attackSpeed: Double
    )

    data class Tag(
        val tagName: String,
        val tagImageUrl: String
    )

    data class ImageUrl(
        val passive: String,
        val square: String,
        val splash: String,
        val loading: String,
        val spell: String,
    )

    data class Passive(
        val name: String,
        val description: String,
        val imageUrl: ImageUrl
    )

    data class Spell(
        val id: String,
        val name: String,
        val description: String,
        val tooltip: String,
        val levelTip: LevelTip? = null,
        val maxRank: Int,
        val coolDown: List<Float>,
        val coolDownBurn: String,
        val cost: List<Int>,
        val costBurn: String,
        val effect: List<List<Float>?>,
        val effectBurn: List<String?>,
        val costType: String,
        val maxammo: String,
        val range: List<Int>,
        val rangeBurn: String?,
        val image: ImageUrl,
        val resource: String
    ) {
        data class LevelTip(
            val label: List<String>?,
            val effect: List<String>?
        )
    }

    data class Skin(
        val id: String,
        val num: Int,
        val name: String,
        val chromas: Boolean,
        val splashUrl: String
    )
}
