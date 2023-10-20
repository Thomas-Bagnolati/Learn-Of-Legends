package com.bagnolati.learnoflegends.core.model

data class Item(
    val id: Int,
    val name: String,
    val plaintext: String,
    val description: String,
    val image: Image,
    val tags: List<String>,
    val colloq: String,
    val gold: Gold,
    val stats: Stats,
    val from: List<String> = emptyList(),
    val into: List<String> = emptyList(),
    val category: Category,
    val depth: Int,
    val maps: Maps
) {

    data class Gold(
        val base: Int,
        val purchasable: Boolean,
        val sell: Int,
        val total: Int,
    )

    data class Image(
        val full: String?,
    )

    data class Maps(
        val summonersRift: Boolean,
        val aram: Boolean,
        val nexusBlitz: Boolean,
        val teamFightTactics: Boolean,
        val arena: Boolean
    )

    enum class Category {
        STARTER,
        CONSUMABLE,
        JUNGLE,
        TRINKET,
        DISTRIBUTED,
        BOOT,
        BASIC,
        EPIC,
        LEGENDARY,
        MYTHIC,
        ORNN,
        EXCLUSIVE,
        MINIONS_TURRET,
        UNKNOWN,

        REMOVED
    }

    data class Stats(
        // Health and Resource
        val health: Double? = null,
        val mana: Double? = null,

        // Ability Enhancements
        val abilityPower: Double? = null,
        val physicalDamage: Double? = null,
        val percentAttackSpeed: Double? = null,
        val percentCritChance: Double? = null,
        val percentCritDamage: Double? = null,

        // Sustain and Regeneration
        val percentLifeSteal: Double? = null,
        val percentHealAndShieldPower: Double? = null,

        // Armor and Magic Penetration
        val lethality: Double? = null,
        val percentArmorPenetration: Double? = null,
        val percentMagicPenetration: Double? = null,

        // Defensive Stats
        val armor: Double? = null,
        val magicResist: Double? = null,

        // Regeneration and Sustainability
        val healthRegen: Double? = null,
        val percentHealthRegen: Double? = null,
        val manaRegen: Double? = null,
        val percentManaRegen: Double? = null,
        val percentOmnivamp: Double? = null,

        // Utility
        val moveSpeed: Double? = null,
        val percentMoveSpeed: Double? = null,
        val abilityHaste: Double? = null,
        val percentTenacity: Double? = null,
        val goldPer10Seconds: Double? = null,
    )
}
