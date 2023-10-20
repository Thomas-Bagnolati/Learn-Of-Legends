package com.bagnolati.learnoflegends.feature.items

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.learnoflegends.feature.items.ItemsSort.*
import com.bagnolati.learnoflegends.core.ui.R as uiR

enum class ItemsSort(
    @StringRes val titleRes: Int,
    @DrawableRes val icon: Int?,
    val iconVisibleOnDark : Boolean = true
) {
    // Base
    DEFAULT(titleRes = R.string.sort_default, null),
    GOLD(titleRes = R.string.sort_gold, icon = uiR.drawable.gold_icon2),

    // Health and Mana
    HEALTH(titleRes = R.string.sort_health, icon = uiR.drawable.stat_health_scaling_icon),
    MANA(titleRes = R.string.sort_mana, icon = uiR.drawable.stat_mana_icon),

    // Ability Enhancements
    ABILITY_POWER(titleRes = R.string.sort_ability_power, icon = uiR.drawable.stat_ability_power_icon),
    PHYSICAL_DAMAGE(titleRes = R.string.sort_physical_damage, icon = uiR.drawable.stat_attack_damage_icon),
    PERCENT_ATTACK_SPEED(titleRes = R.string.sort_attack_speed, icon = uiR.drawable.stat_attack_speed_icon),
    PERCENT_CRIT_CHANCE(titleRes = R.string.sort_percent_crit_chance, icon = uiR.drawable.stat_crit_chance_icon),
    PERCENT_CRIT_DAMAGE(titleRes = R.string.sort_percent_crit_damage,icon = uiR.drawable.stat_crit_damage_icon, false),

    // Penetration
    LETHALITY(titleRes = R.string.sort_lethality, icon = uiR.drawable.stat_armor_pen_icon),
    PERCENT_ARMOR_PENETRATION(titleRes = R.string.sort_percent_armor_penetration, icon = uiR.drawable.stat_armor_pen_icon),
    PERCENT_MAGIC_PENETRATION(titleRes = R.string.sort_percent_magic_penetration, icon = uiR.drawable.stat_magic_pen_icon),

    // Defensive Stats
    ARMOR(titleRes = R.string.sort_armor, icon = uiR.drawable.stat_armor_icon),
    MAGIC_RESIST(titleRes = R.string.sort_magic_resist, icon = uiR.drawable.stat_magic_res_icon),

    // Regeneration and Sustainability
    PERCENT_BASE_HEALTH_REGEN(titleRes = R.string.sort_percent_base_health_regen,icon = uiR.drawable.stat_base_health_regen_icon),
    PERCENT_BASE_MANA_REGEN(titleRes = R.string.sort_percent_base_mana_regen, icon = uiR.drawable.stat_base_mana_regen_icon),
    PERCENT_OMNIVAMP(titleRes = R.string.sort_percent_omnivamp, icon = uiR.drawable.stat_omnivamp_icon),
    PERCENT_LIFESTEAL(titleRes = R.string.sort_lifesteal, icon = uiR.drawable.stat_lifesteal_icon),
    PERCENT_HEAL_AND_SHIELD_POWER(titleRes = R.string.sort_percent_heal_and_shield_power, icon = uiR.drawable.stat_heal_and_shield_power_icon, false),

    // Utility
    MOVE_SPEED(titleRes = R.string.sort_move_speed, icon = uiR.drawable.stat_move_speed_icon, false),
    PERCENT_MOVE_SPEED(titleRes = R.string.sort_percent_move_speed, icon = uiR.drawable.stat_move_speed_icon, false),
    ABILITY_HASTE(titleRes = R.string.sort_ability_haste, icon = uiR.drawable.stat_ability_haste_icon,false),
    PERCENT_TENACITY(titleRes = R.string.sort_percent_tenacity, icon = uiR.drawable.stat_tenacity_icon),
    GOLD_PER_10_SECONDS(titleRes = R.string.sort_gold_per_10_seconds, icon = uiR.drawable.gold_icon),

}

fun Item.getStatBySortItem(sort: ItemsSort): Double {
    return when (sort) {
        // Default sorting
        DEFAULT -> 1.0

        // Gold category
        GOLD -> if (gold.purchasable) gold.total.toDouble() else 0.0

        // Health and Resource
        HEALTH -> stats.health ?: 0.0
        MANA -> stats.mana ?: 0.0

        // Ability Enhancements
        ABILITY_POWER -> stats.abilityPower ?: 0.0
        PHYSICAL_DAMAGE -> stats.physicalDamage ?: 0.0
        PERCENT_ATTACK_SPEED -> stats.percentAttackSpeed ?: 0.0
        PERCENT_CRIT_CHANCE -> stats.percentCritChance ?: 0.0
        PERCENT_CRIT_DAMAGE -> stats.percentCritDamage ?: 0.0

        // Sustain and Regeneration
        PERCENT_LIFESTEAL -> stats.percentLifeSteal ?: 0.0
        PERCENT_HEAL_AND_SHIELD_POWER -> stats.percentHealAndShieldPower ?: 0.0

        // Armor and Magic Penetration
        LETHALITY -> stats.lethality ?: 0.0
        PERCENT_ARMOR_PENETRATION -> stats.percentArmorPenetration ?: 0.0
        PERCENT_MAGIC_PENETRATION -> stats.percentMagicPenetration ?: 0.0

        // Defensive Stats
        ARMOR -> stats.armor ?: 0.0
        MAGIC_RESIST -> stats.magicResist ?: 0.0

        // Regeneration and Sustainability
        PERCENT_BASE_HEALTH_REGEN -> stats.percentHealthRegen ?: 0.0
        PERCENT_BASE_MANA_REGEN -> stats.percentManaRegen ?: 0.0
        PERCENT_OMNIVAMP -> stats.percentOmnivamp ?: 0.0

        // Utility
        MOVE_SPEED -> stats.moveSpeed ?: 0.0
        PERCENT_MOVE_SPEED -> stats.percentMoveSpeed ?: 0.0
        ABILITY_HASTE -> stats.abilityHaste ?: 0.0
        PERCENT_TENACITY -> stats.percentTenacity ?: 0.0
        GOLD_PER_10_SECONDS -> stats.goldPer10Seconds ?: 0.0
    }
}