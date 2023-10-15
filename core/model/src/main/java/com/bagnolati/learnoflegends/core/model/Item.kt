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
        val flatArmorMod: Double? = null,
        val flatAttackSpeedMod: Double? = null,
        val flatBlockMod: Double? = null,
        val flatCritChanceMod: Double? = null,
        val flatCritDamageMod: Double? = null,
        val flatEXPBonus: Double? = null,
        val flatEnergyPoolMod: Double? = null,
        val flatEnergyRegenMod: Double? = null,
        val flatHPPoolMod: Double? = null,
        val flatHPRegenMod: Double? = null,
        val flatMPPoolMod: Double? = null,
        val flatMPRegenMod: Double? = null,
        val flatMagicDamageMod: Double? = null,
        val flatMovementSpeedMod: Double? = null,
        val flatPhysicalDamageMod: Double? = null,
        val flatSpellBlockMod: Double? = null,
        val percentArmorMod: Double? = null,
        val percentAttackSpeedMod: Double? = null,
        val percentBlockMod: Double? = null,
        val percentCritChanceMod: Double? = null,
        val percentCritDamageMod: Double? = null,
        val percentDodgeMod: Double? = null,
        val percentEXPBonus: Double? = null,
        val percentHPPoolMod: Double? = null,
        val percentHPRegenMod: Double? = null,
        val percentLifeStealMod: Double? = null,
        val percentMPPoolMod: Double? = null,
        val percentMPRegenMod: Double? = null,
        val percentMagicDamageMod: Double? = null,
        val percentMovementSpeedMod: Double? = null,
        val percentPhysicalDamageMod: Double? = null,
        val percentSpellBlockMod: Double? = null,
        val percentSpellVampMod: Double? = null,
        val rFlatArmorModPerLevel: Double? = null,
        val rFlatArmorPenetrationMod: Double? = null,
        val rFlatArmorPenetrationModPerLevel: Double? = null,
        val rFlatCritChanceModPerLevel: Double? = null,
        val rFlatCritDamageModPerLevel: Double? = null,
        val rFlatDodgeMod: Double? = null,
        val rFlatDodgeModPerLevel: Double? = null,
        val rFlatEnergyModPerLevel: Double? = null,
        val rFlatEnergyRegenModPerLevel: Double? = null,
        val rFlatGoldPer10Mod: Double? = null,
        val rFlatHPModPerLevel: Double? = null,
        val rFlatHPRegenModPerLevel: Double? = null,
        val rFlatMPModPerLevel: Double? = null,
        val rFlatMPRegenModPerLevel: Double? = null,
        val rFlatMagicDamageModPerLevel: Double? = null,
        val rFlatMagicPenetrationMod: Double? = null,
        val rFlatMagicPenetrationModPerLevel: Double? = null,
        val rFlatMovementSpeedModPerLevel: Double? = null,
        val rFlatPhysicalDamageModPerLevel: Double? = null,
        val rFlatSpellBlockModPerLevel: Double? = null,
        val rFlatTimeDeadMod: Double? = null,
        val rFlatTimeDeadModPerLevel: Double? = null,
        val rPercentArmorPenetrationMod: Double? = null,
        val rPercentArmorPenetrationModPerLevel: Double? = null,
        val rPercentAttackSpeedModPerLevel: Double? = null,
        val rPercentCooldownMod: Double? = null,
        val rPercentCooldownModPerLevel: Double? = null,
        val rPercentMagicPenetrationMod: Double? = null,
        val rPercentMagicPenetrationModPerLevel: Double? = null,
        val rPercentMovementSpeedModPerLevel: Double? = null,
        val rPercentTimeDeadMod: Double? = null,
        val rPercentTimeDeadModPerLevel: Double? = null
    )
}
