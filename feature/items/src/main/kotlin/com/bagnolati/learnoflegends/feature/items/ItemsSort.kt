package com.bagnolati.learnoflegends.feature.items

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.learnoflegends.core.ui.R as uiR

enum class ItemsSort(
    @StringRes val titleRes: Int,
    @DrawableRes val icon: Int?
) {
    DEFAULT(titleRes = R.string.sort_default, icon = null),
    GOLD(titleRes = R.string.sort_gold, icon = uiR.drawable.gold_icon),
    FLAT_MAGIC_DAMAGE(titleRes = R.string.sort_ap, icon = uiR.drawable.stat_ability_power_icon),
    FLAT_PHYSICAL_DAMAGE(titleRes = R.string.sort_ad, icon = uiR.drawable.stat_attack_damage_icon),
    PERCENT_ATTACK_SPEED(titleRes = R.string.sort_attack_speed, icon = uiR.drawable.stat_attack_speed_icon),
    PERCENT_LIFESTEAL(titleRes = R.string.sort_lifesteal, icon = uiR.drawable.stat_lifesteal_icon),
    FLAT_CRIT_CHANCE(titleRes = R.string.sort_crit_chance, icon = uiR.drawable.stat_crit_icon),
    FLAT_HP_POOL(titleRes = R.string.sort_hp, icon = uiR.drawable.stat_health_scaling_icon),
    FLAT_MP_POOL(titleRes = R.string.sort_mp, icon = uiR.drawable.stat_mana_icon),
    FLAT_ARMOR(titleRes = R.string.sort_armor, icon = uiR.drawable.stat_armor_icon),
    FLAT_MAGIC_RESIST(titleRes = R.string.sort_magic_resist, icon = uiR.drawable.stat_magic_res_icon),
    FLAT_MOVEMENT_SPEED(titleRes = R.string.sort_move_speed, icon = uiR.drawable.stat_movement_speed_icon),
    PERCENT_MOVEMENT_SPEED(titleRes = R.string.sort_move_speed_percent, icon = uiR.drawable.stat_movement_speed_icon),
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
