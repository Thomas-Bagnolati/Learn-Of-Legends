package com.bagnolati.learnoflegends.core.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkItem(
    val id: Int = -1,
    val colloq: String,
    val description: String,
    val gold: NetworkGold,
    val image: NetworkImage,
    val from: List<String> = emptyList(),
    val into: List<String> = emptyList(),
    val maps: NetworkMaps,
    val name: String,
    val plaintext: String,
    val stats: NetworkStats,
    val tags: List<String>,
    val depth: Int? = null,
) {

    @Serializable
    data class NetworkGold(
        val base: Int,
        val purchasable: Boolean,
        val sell: Int,
        val total: Int
    )

    @Serializable
    data class NetworkImage(
        val full: String,
        val group: String,
        val h: Int,
        val sprite: String,
        val w: Int,
        val x: Int,
        val y: Int
    )

    @Serializable
    data class NetworkMaps(
        @SerialName("11")
        val x11: Boolean,
        @SerialName("12")
        val x12: Boolean,
        @SerialName("21")
        val x21: Boolean,
        @SerialName("22")
        val x22: Boolean,
        @SerialName("30")
        val x30: Boolean
    )

    @Serializable
    data class NetworkStats(
        @SerialName("FlatArmorMod")
        val flatArmorMod: Double? = null,
        @SerialName("FlatAttackSpeedMod")
        val flatAttackSpeedMod: Double? = null,
        @SerialName("FlatBlockMod")
        val flatBlockMod: Double? = null,
        @SerialName("FlatCritChanceMod")
        val flatCritChanceMod: Double? = null,
        @SerialName("FlatCritDamageMod")
        val flatCritDamageMod: Double? = null,
        @SerialName("FlatEXPBonus")
        val flatEXPBonus: Double? = null,
        @SerialName("FlatEnergyPoolMod")
        val flatEnergyPoolMod: Double? = null,
        @SerialName("FlatEnergyRegenMod")
        val flatEnergyRegenMod: Double? = null,
        @SerialName("FlatHPPoolMod")
        val flatHPPoolMod: Double? = null,
        @SerialName("FlatHPRegenMod")
        val flatHPRegenMod: Double? = null,
        @SerialName("FlatMPPoolMod")
        val flatMPPoolMod: Double? = null,
        @SerialName("FlatMPRegenMod")
        val flatMPRegenMod: Double? = null,
        @SerialName("FlatMagicDamageMod")
        val flatMagicDamageMod: Double? = null,
        @SerialName("FlatMovementSpeedMod")
        val flatMovementSpeedMod: Double? = null,
        @SerialName("FlatPhysicalDamageMod")
        val flatPhysicalDamageMod: Double? = null,
        @SerialName("FlatSpellBlockMod")
        val flatSpellBlockMod: Double? = null,
        @SerialName("PercentArmorMod")
        val percentArmorMod: Double? = null,
        @SerialName("PercentAttackSpeedMod")
        val percentAttackSpeedMod: Double? = null,
        @SerialName("PercentBlockMod")
        val percentBlockMod: Double? = null,
        @SerialName("PercentCritChanceMod")
        val percentCritChanceMod: Double? = null,
        @SerialName("PercentCritDamageMod")
        val percentCritDamageMod: Double? = null,
        @SerialName("PercentDodgeMod")
        val percentDodgeMod: Double? = null,
        @SerialName("PercentEXPBonus")
        val percentEXPBonus: Double? = null,
        @SerialName("PercentHPPoolMod")
        val percentHPPoolMod: Double? = null,
        @SerialName("PercentHPRegenMod")
        val percentHPRegenMod: Double? = null,
        @SerialName("PercentLifeStealMod")
        val percentLifeStealMod: Double? = null,
        @SerialName("PercentMPPoolMod")
        val percentMPPoolMod: Double? = null,
        @SerialName("PercentMPRegenMod")
        val percentMPRegenMod: Double? = null,
        @SerialName("PercentMagicDamageMod")
        val percentMagicDamageMod: Double? = null,
        @SerialName("PercentMovementSpeedMod")
        val percentMovementSpeedMod: Double? = null,
        @SerialName("PercentPhysicalDamageMod")
        val percentPhysicalDamageMod: Double? = null,
        @SerialName("PercentSpellBlockMod")
        val percentSpellBlockMod: Double? = null,
        @SerialName("PercentSpellVampMod")
        val percentSpellVampMod: Double? = null,
        @SerialName("rFlatArmorModPerLevel")
        val rFlatArmorModPerLevel: Double? = null,
        @SerialName("rFlatArmorPenetrationMod")
        val rFlatArmorPenetrationMod: Double? = null,
        @SerialName("rFlatArmorPenetrationModPerLevel")
        val rFlatArmorPenetrationModPerLevel: Double? = null,
        @SerialName("rFlatCritChanceModPerLevel")
        val rFlatCritChanceModPerLevel: Double? = null,
        @SerialName("rFlatCritDamageModPerLevel")
        val rFlatCritDamageModPerLevel: Double? = null,
        @SerialName("rFlatDodgeMod")
        val rFlatDodgeMod: Double? = null,
        @SerialName("rFlatDodgeModPerLevel")
        val rFlatDodgeModPerLevel: Double? = null,
        @SerialName("rFlatEnergyModPerLevel")
        val rFlatEnergyModPerLevel: Double? = null,
        @SerialName("rFlatEnergyRegenModPerLevel")
        val rFlatEnergyRegenModPerLevel: Double? = null,
        @SerialName("rFlatGoldPer10Mod")
        val rFlatGoldPer10Mod: Double? = null,
        @SerialName("rFlatHPModPerLevel")
        val rFlatHPModPerLevel: Double? = null,
        @SerialName("rFlatHPRegenModPerLevel")
        val rFlatHPRegenModPerLevel: Double? = null,
        @SerialName("rFlatMPModPerLevel")
        val rFlatMPModPerLevel: Double? = null,
        @SerialName("rFlatMPRegenModPerLevel")
        val rFlatMPRegenModPerLevel: Double? = null,
        @SerialName("rFlatMagicDamageModPerLevel")
        val rFlatMagicDamageModPerLevel: Double? = null,
        @SerialName("rFlatMagicPenetrationMod")
        val rFlatMagicPenetrationMod: Double? = null,
        @SerialName("rFlatMagicPenetrationModPerLevel")
        val rFlatMagicPenetrationModPerLevel: Double? = null,
        @SerialName("rFlatMovementSpeedModPerLevel")
        val rFlatMovementSpeedModPerLevel: Double? = null,
        @SerialName("rFlatPhysicalDamageModPerLevel")
        val rFlatPhysicalDamageModPerLevel: Double? = null,
        @SerialName("rFlatSpellBlockModPerLevel")
        val rFlatSpellBlockModPerLevel: Double? = null,
        @SerialName("rFlatTimeDeadMod")
        val rFlatTimeDeadMod: Double? = null,
        @SerialName("rFlatTimeDeadModPerLevel")
        val rFlatTimeDeadModPerLevel: Double? = null,
        @SerialName("rPercentArmorPenetrationMod")
        val rPercentArmorPenetrationMod: Double? = null,
        @SerialName("rPercentArmorPenetrationModPerLevel")
        val rPercentArmorPenetrationModPerLevel: Double? = null,
        @SerialName("rPercentAttackSpeedModPerLevel")
        val rPercentAttackSpeedModPerLevel: Double? = null,
        @SerialName("rPercentCooldownMod")
        val rPercentCooldownMod: Double? = null,
        @SerialName("rPercentCooldownModPerLevel")
        val rPercentCooldownModPerLevel: Double? = null,
        @SerialName("rPercentMagicPenetrationMod")
        val rPercentMagicPenetrationMod: Double? = null,
        @SerialName("rPercentMagicPenetrationModPerLevel")
        val rPercentMagicPenetrationModPerLevel: Double? = null,
        @SerialName("rPercentMovementSpeedModPerLevel")
        val rPercentMovementSpeedModPerLevel: Double? = null,
        @SerialName("rPercentTimeDeadMod")
        val rPercentTimeDeadMod: Double? = null,
        @SerialName("rPercentTimeDeadModPerLevel")
        val rPercentTimeDeadModPerLevel: Double? = null
    )

}