package com.bagnolati.nutrigood.core.domain.mapper

import com.bagnolati.learnoflegends.core.model.Item
import com.bagnolati.learnoflegends.core.model.Item.Category.BASIC
import com.bagnolati.learnoflegends.core.model.Item.Category.BOOT
import com.bagnolati.learnoflegends.core.model.Item.Category.CONSUMABLE
import com.bagnolati.learnoflegends.core.model.Item.Category.DISTRIBUTED
import com.bagnolati.learnoflegends.core.model.Item.Category.EPIC
import com.bagnolati.learnoflegends.core.model.Item.Category.EXCLUSIVE
import com.bagnolati.learnoflegends.core.model.Item.Category.JUNGLE
import com.bagnolati.learnoflegends.core.model.Item.Category.LEGENDARY
import com.bagnolati.learnoflegends.core.model.Item.Category.MINIONS_TURRET
import com.bagnolati.learnoflegends.core.model.Item.Category.MYTHIC
import com.bagnolati.learnoflegends.core.model.Item.Category.ORNN
import com.bagnolati.learnoflegends.core.model.Item.Category.REMOVED
import com.bagnolati.learnoflegends.core.model.Item.Category.STARTER
import com.bagnolati.learnoflegends.core.model.Item.Category.TRINKET
import com.bagnolati.learnoflegends.core.model.Item.Category.UNKNOWN
import com.bagnolati.learnoflegends.core.network.DdragonUrl
import com.bagnolati.learnoflegends.core.network.model.NetworkItem


/**
 * Map [NetworkItem] to [Item]
 */
fun NetworkItem.asItem(): Item = Item(
    id = id,
    name = name,
    plaintext = plaintext,
    description = description,
    image = image.asImage(),
    tags = tags,
    gold = gold.asGold(),
    colloq = colloq,
    stats = stats.asStats(),
    from = from,
    into = into,
    category = getCategory(),
    depth = depth ?: 1,
    maps = maps.asMaps(),
)

fun NetworkItem.getCategory(): Item.Category {
    return when (id) {
        3070 -> STARTER // Tear of the Goddess

        2419 -> DISTRIBUTED // Commencing Stopwatch
        2422 -> DISTRIBUTED // Slightly Magical Footwear
        2423 -> DISTRIBUTED // Perfectly Timed Stopwatch
        3400 -> DISTRIBUTED // Your Cut

        3330 -> EXCLUSIVE // Scarecrow Effigy
        3600 -> EXCLUSIVE // Black Spear
        3901 -> EXCLUSIVE // GP
        3902 -> EXCLUSIVE // GP
        3903 -> EXCLUSIVE // GP

        1036 -> BASIC // Long Sword
        2421 -> BASIC // Broken Stopwatch

        3851 -> EPIC // Frostfang
        3855 -> EPIC // Runestel Spaulder
        3859 -> EPIC // Targon's Buckler
        3863 -> EPIC // Harrowing Cresent
        4638 -> EPIC // Watchful WardStone

        3040 -> LEGENDARY // Seraph's Embrace
        3041 -> LEGENDARY // Mejai's Soulstealer
        3042 -> LEGENDARY // Muramana
        3089 -> LEGENDARY // Rabadon's
        3121 -> LEGENDARY // Fimbulwinter
        3853 -> LEGENDARY // Shard of True Ice
        3857 -> LEGENDARY // Pauldrons of Whiterock
        3860 -> LEGENDARY // Bulvark of the Mountain
        3864 -> LEGENDARY // Black Mist Scythe
        4643 -> LEGENDARY // Vigilant Wardstone
        6693 -> LEGENDARY // Prowler's Claw

        3031 -> MYTHIC // Infinity Edge

        1500 -> MINIONS_TURRET // Penetration Bullets
        1501 -> MINIONS_TURRET // Fortification
        1502 -> MINIONS_TURRET // Reinforced Armor
        1503 -> MINIONS_TURRET // Warden's Eyes
        1506 -> MINIONS_TURRET // Reinforced Armor
        1507 -> MINIONS_TURRET // Overcharged
        1508 -> MINIONS_TURRET // Anti-tower Shocks
        1509 -> MINIONS_TURRET // Gusto
        1510 -> MINIONS_TURRET // Phreakish Gusto
        1511 -> MINIONS_TURRET // Super Mech Armor
        1512 -> MINIONS_TURRET // Super Mech Power Field
        1515 -> MINIONS_TURRET // Turret Plating
        1520 -> MINIONS_TURRET // Overcharged HA
        1521 -> MINIONS_TURRET // Fortification
        1522 -> MINIONS_TURRET // Turret Power UP

        1040 -> REMOVED // Obsidian Edge
        4641 -> REMOVED // Stirring Wardstone
        7000 -> REMOVED // Sandshrike's Claw

        2019 -> UNKNOWN // Steel Sigil
        3599 -> UNKNOWN // Black Spear
        7050 -> UNKNOWN // GP placeholder

        else ->
            if (tags.isEmpty()) DISTRIBUTED
            else if (tags.contains("Boots")) BOOT
            else if (tags.contains("Trinket")) TRINKET
            else if (tags.contains("Consumable")) CONSUMABLE
            else if (tags.contains("Jungle")) JUNGLE
            else if (tags.contains("Lane")) STARTER
            else if (description.contains("ornnBonus")) ORNN
            else if (depth == null && gold.purchasable) BASIC
            else if (depth == 2) EPIC
            else if (from.isNotEmpty() && into.isEmpty()) LEGENDARY
            else if (from.isNotEmpty() && into.count() == 1) MYTHIC
            else UNKNOWN
    }
}


/**
 * Map [NetworkItem.NetworkMaps] to [Item.Maps]
 */
fun NetworkItem.NetworkMaps.asMaps(): Item.Maps = Item.Maps(
    summonersRift = x11,
    aram = x12,
    nexusBlitz = x21,
    teamFightTactics = x22,
    arena = x30
)

/**
 * Map [NetworkItem.NetworkImage] to [Item.Image]
 */
fun NetworkItem.NetworkImage.asImage(): Item.Image = Item.Image(
    full = DdragonUrl.ITEM_IMAGE_URL + full,
)

/**
 * Map [NetworkItem.NetworkGold] to [Item.Gold]
 */
fun NetworkItem.NetworkGold.asGold(): Item.Gold = Item.Gold(
    base = base,
    purchasable = purchasable,
    sell = sell,
    total = total
)

/**
 * Map [NetworkItem.NetworkStats] to [Item.Stats]
 */
fun NetworkItem.NetworkStats.asStats(): Item.Stats = Item.Stats(
    flatArmorMod = flatArmorMod,
    flatAttackSpeedMod = flatAttackSpeedMod,
    flatBlockMod = flatBlockMod,
    flatCritChanceMod = flatCritChanceMod,
    flatCritDamageMod = flatCritDamageMod,
    flatEXPBonus = flatEXPBonus,
    flatEnergyPoolMod = flatEnergyPoolMod,
    flatEnergyRegenMod = flatEnergyRegenMod,
    flatHPPoolMod = flatHPPoolMod,
    flatHPRegenMod = flatHPRegenMod,
    flatMPPoolMod = flatMPPoolMod,
    flatMPRegenMod = flatMPRegenMod,
    flatMagicDamageMod = flatMagicDamageMod,
    flatMovementSpeedMod = flatMovementSpeedMod,
    flatPhysicalDamageMod = flatPhysicalDamageMod,
    flatSpellBlockMod = flatSpellBlockMod,
    percentArmorMod = percentArmorMod,
    percentAttackSpeedMod = percentAttackSpeedMod,
    percentBlockMod = percentBlockMod,
    percentCritChanceMod = percentCritChanceMod,
    percentCritDamageMod = percentCritDamageMod,
    percentDodgeMod = percentDodgeMod,
    percentEXPBonus = percentEXPBonus,
    percentHPPoolMod = percentHPPoolMod,
    percentHPRegenMod = percentHPRegenMod,
    percentLifeStealMod = percentLifeStealMod,
    percentMPPoolMod = percentMPPoolMod,
    percentMPRegenMod = percentMPRegenMod,
    percentMagicDamageMod = percentMagicDamageMod,
    percentMovementSpeedMod = percentMovementSpeedMod,
    percentPhysicalDamageMod = percentPhysicalDamageMod,
    percentSpellBlockMod = percentSpellBlockMod,
    percentSpellVampMod = percentSpellVampMod,
    rFlatArmorModPerLevel = rFlatArmorModPerLevel,
    rFlatArmorPenetrationMod = rFlatArmorPenetrationMod,
    rFlatArmorPenetrationModPerLevel = rFlatArmorPenetrationModPerLevel,
    rFlatCritChanceModPerLevel = rFlatCritChanceModPerLevel,
    rFlatCritDamageModPerLevel = rFlatCritDamageModPerLevel,
    rFlatDodgeMod = rFlatDodgeMod,
    rFlatDodgeModPerLevel = rFlatDodgeModPerLevel,
    rFlatEnergyModPerLevel = rFlatEnergyModPerLevel,
    rFlatEnergyRegenModPerLevel = rFlatEnergyRegenModPerLevel,
    rFlatGoldPer10Mod = rFlatGoldPer10Mod,
    rFlatHPModPerLevel = rFlatHPModPerLevel,
    rFlatHPRegenModPerLevel = rFlatHPRegenModPerLevel,
    rFlatMPModPerLevel = rFlatMPModPerLevel,
    rFlatMPRegenModPerLevel = rFlatMPRegenModPerLevel,
    rFlatMagicDamageModPerLevel = rFlatMagicDamageModPerLevel,
    rFlatMagicPenetrationMod = rFlatMagicPenetrationMod,
    rFlatMagicPenetrationModPerLevel = rFlatMagicPenetrationModPerLevel,
    rFlatMovementSpeedModPerLevel = rFlatMovementSpeedModPerLevel,
    rFlatPhysicalDamageModPerLevel = rFlatPhysicalDamageModPerLevel,
    rFlatSpellBlockModPerLevel = rFlatSpellBlockModPerLevel,
    rFlatTimeDeadMod = rFlatTimeDeadMod,
    rFlatTimeDeadModPerLevel = rFlatTimeDeadModPerLevel,
    rPercentArmorPenetrationMod = rPercentArmorPenetrationMod,
    rPercentArmorPenetrationModPerLevel = rPercentArmorPenetrationModPerLevel,
    rPercentAttackSpeedModPerLevel = rPercentAttackSpeedModPerLevel,
    rPercentCooldownMod = rPercentCooldownMod,
    rPercentCooldownModPerLevel = rPercentCooldownModPerLevel,
    rPercentMagicPenetrationMod = rPercentMagicPenetrationMod,
    rPercentMagicPenetrationModPerLevel = rPercentMagicPenetrationModPerLevel,
    rPercentMovementSpeedModPerLevel = rPercentMovementSpeedModPerLevel,
    rPercentTimeDeadMod = rPercentTimeDeadMod,
    rPercentTimeDeadModPerLevel = rPercentTimeDeadModPerLevel
)