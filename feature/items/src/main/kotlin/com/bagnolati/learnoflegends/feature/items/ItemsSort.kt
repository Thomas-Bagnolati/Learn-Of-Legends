package com.bagnolati.learnoflegends.feature.items

import com.bagnolati.learnoflegends.core.model.Item

enum class ItemsSort {
    DEFAULT,
    GOLD,
    FLAT_HP_POOL,
    FLAT_MP_POOL,
    FLAT_ARMOR,
    FLAT_MAGIC_RESIST,
    FLAT_MOVEMENT_SPEED,
    PERCENT_MOVEMENT_SPEED,
    FLAT_PHYSICAL_DAMAGE,
    FLAT_MAGIC_DAMAGE,
    FLAT_CRIT_CHANCE,
    PERCENT_ATTACK_SPEED,
    PERCENT_LIFESTEAL,
}

fun Item.getStatBySortItem(sort: ItemsSort): Double {
    return when (sort) {
        ItemsSort.DEFAULT -> 1.0

        ItemsSort.FLAT_ARMOR -> stats.flatArmorMod ?: 0.0
//          ItemsSort.FLAT_ATTACK_SPEED_MOD -> stats.flatAttackSpeedMod ?: 0.0
//          ItemsSort.FLAT_BLOCK_MOD -> stats.flatBlockMod ?: 0.0
        ItemsSort.FLAT_CRIT_CHANCE -> stats.flatCritChanceMod ?: 0.0
//          ItemsSort.FLAT_CRIT_DAMAGE_MOD -> stats.flatCritDamageMod ?: 0.0
//          ItemsSort.FLAT_EXP_BONUS -> stats.flatEXPBonus ?: 0.0
//          ItemsSort.FLAT_ENERGY_POOL_MOD -> stats.flatEnergyPoolMod ?: 0.0
//          ItemsSort.FLAT_ENERGY_REGEN_MOD -> stats.flatEnergyRegenMod ?: 0.0
        ItemsSort.FLAT_HP_POOL -> stats.flatHPPoolMod ?: 0.0
//          ItemsSort.FLAT_HP_REGEN_MOD -> stats.flatEnergyRegenMod ?: 0.0
        ItemsSort.FLAT_MP_POOL -> stats.flatMPPoolMod ?: 0.0
//          ItemsSort.FLAT_MP_REGEN_MOD -> stats.flatMPRegenMod ?: 0.0
        ItemsSort.FLAT_MAGIC_DAMAGE -> stats.flatMagicDamageMod ?: 0.0
        ItemsSort.FLAT_MOVEMENT_SPEED -> stats.flatMovementSpeedMod ?: 0.0
        ItemsSort.FLAT_PHYSICAL_DAMAGE -> stats.flatPhysicalDamageMod ?: 0.0
        ItemsSort.FLAT_MAGIC_RESIST -> stats.flatSpellBlockMod ?: 0.0
//          ItemsSort.PERCENT_ARMOR_MOD -> stats.percentArmorMod ?: 0.0
        ItemsSort.PERCENT_ATTACK_SPEED -> stats.percentAttackSpeedMod ?: 0.0
//          ItemsSort.PERCENT_BLOCK_MOD -> stats.percentBlockMod ?: 0.0
//          ItemsSort.PERCENT_CRIT_CHANCE_MOD -> stats.percentCritChanceMod ?: 0.0
//          ItemsSort.PERCENT_CRIT_DAMAGE_MOD -> stats.percentCritDamageMod ?: 0.0
//          ItemsSort.PERCENT_DODGE_MOD -> stats.percentDodgeMod ?: 0.0
//          ItemsSort.PERCENT_EXP_BONUS -> stats.percentEXPBonus ?: 0.0
//          ItemsSort.PERCENT_HP_POOL_MOD -> stats.percentHPPoolMod ?: 0.0
//          ItemsSort.PERCENT_HP_REGEN_MOD -> stats.percentHPRegenMod ?: 0.0
        ItemsSort.PERCENT_LIFESTEAL -> stats.percentLifeStealMod ?: 0.0
//          ItemsSort.PERCENT_MP_POOL_MOD -> stats.percentMPPoolMod ?: 0.0
//          ItemsSort.PERCENT_MP_REGEN_MOD -> stats.percentMPRegenMod ?: 0.0
//          ItemsSort.PERCENT_MAGIC_DAMAGE_MOD -> stats.percentMagicDamageMod ?: 0.0
        ItemsSort.PERCENT_MOVEMENT_SPEED -> stats.percentMovementSpeedMod ?: 0.0
//          ItemsSort.PERCENT_PHYSICAL_DAMAGE_MOD -> stats.percentPhysicalDamageMod ?: 0.0
//          ItemsSort.PERCENT_SPELLBLOCK_MOD -> stats.percentSpellBlockMod ?: 0.0
//          ItemsSort.PERCENT_SPELLVAMP_MOD -> stats.percentSpellVampMod ?: 0.0
        ItemsSort.GOLD ->
            if (gold.purchasable) gold.total.toDouble()
            else 0.0
    }
}
