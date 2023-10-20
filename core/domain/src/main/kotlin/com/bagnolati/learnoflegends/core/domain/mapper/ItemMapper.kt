package com.bagnolati.learnoflegends.core.domain.mapper

import com.bagnolati.learnoflegends.core.model.HtmlTag
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
import com.bagnolati.learnoflegends.core.model.closeTag
import com.bagnolati.learnoflegends.core.model.openTag
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
    stats = stats.asStats(description),
    from = from,
    into = into,
    category = getCategory(),
    depth = depth ?: 1,
    maps = maps.asMaps(),
)

/**
 * Retrieve a [Item.Category] for each [Item].
 * As the
 */
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

        // Try to keep it more generic but keep in mind it can make mistakes.
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
 *
 * [NetworkItem.NetworkStats] is not relevant to get all stats, lot of stats are missing.
 * So we will extract them from the description text.
 */
fun NetworkItem.NetworkStats.asStats(description: String): Item.Stats {
    var text = description

    // retrieves stat from description string
    // 1 -> get stats text
    text.getTagAndContentOrNull(HtmlTag.STATS)?.let {
        text = it
        // 2 -> remove tags
        text = text.removeTags(HtmlTag.STATS)
        text = text.removeTags(HtmlTag.ATTENTION)
        text = text.removeTags(HtmlTag.ORNN_BONUS)
        text = text.removeOpenTag(HtmlTag.BR)
        text = text.removeTags(HtmlTag.BOLD)
        text = text.removeTags(HtmlTag.BUFFED_STAT)
        text = text.removeTags(HtmlTag.NERFED_STAT)
        text = text.trim()
    }

    return Item.Stats(
        abilityHaste = text.extractStatValueOrNull("Ability Haste"),
        abilityPower = text.extractStatValueOrNull("Ability Power"),
        armor = text.extractStatValueOrNull("Armor"),
        goldPer10Seconds = text.extractStatValueOrNull("Gold Per"),
        health = text.extractStatValueOrNull("Health"),
        healthRegen = text.extractStatValueOrNull("Base Health Regen"),
        lethality = text.extractStatValueOrNull("Lethality"),
        magicResist = text.extractStatValueOrNull("Magic Resist"),
        mana = text.extractStatValueOrNull("Mana"),
        manaRegen = text.extractStatValueOrNull("Base Mana Regen"),
        moveSpeed = text.extractStatValueOrNull("Move Speed") ?: flatMovementSpeedMod, // no <stats> tag on mobility boots.
        percentMoveSpeed = text.extractStatValueOrNull("Move Speed", true),
        physicalDamage = text.extractStatValueOrNull("Attack Damage"),
        percentArmorPenetration = text.extractStatValueOrNull("Armor Penetration", true),
        percentAttackSpeed = text.extractStatValueOrNull("Attack Speed", true),
        percentCritChance = text.extractStatValueOrNull("Critical Strike Chance", true),
        percentCritDamage = text.extractStatValueOrNull("Critical Strike Damage", true),
        percentHealAndShieldPower = text.extractStatValueOrNull("Heal and Shield Power", true),
        percentLifeSteal = text.extractStatValueOrNull("Life Steal", true),
        percentMagicPenetration = text.extractStatValueOrNull("Magic Penetration", true),
        percentManaRegen = text.extractStatValueOrNull("Base Mana Regen", true),
        percentOmnivamp = text.extractStatValueOrNull("Omnivamp", true),
        percentTenacity = text.extractStatValueOrNull("Tenacity", true),
        percentHealthRegen = text.extractStatValueOrNull("Base Health Regen", true),
    )
}

/**
 * Percent are represented as /1 instead of /100
 *
 * @return For 0.2 gives 20.0
 */
fun Double?.asPercent(): Double? {
    if (this == null) return null
    return (this * 100).toInt().toDouble()
}

fun String.removeTags(tag: HtmlTag): String =
    this.replace(tag.openTag(), "")
        .replace(tag.closeTag(), "")

fun String.removeOpenTag(tag: HtmlTag): String =
    replace(tag.openTag(), "")

fun String.getTagAndContentOrNull(tag: HtmlTag): String? {
    val start = indexOf(tag.openTag(), 0)
    val end = indexOf(tag.closeTag(), start) + tag.closeTag().count()
    return if (start >= 0 && start < end) substring(start, end)
    else null
}

fun Char.isCorrectAsValue(): Boolean =
    this == ' ' || this == '%' || this.digitToIntOrNull() != null

fun String.extractStatValueOrNull(stat: String, asPercent: Boolean = false): Double? {
    var stringValue = ""
    var lastIndex = indexOf(stat)

    if (lastIndex <= 0) return null
    if (stat.isEmpty()) return null
    if (!contains((stat))) return null

    do {
        val char = get(lastIndex - 1)
        lastIndex -= 1
        if (char.isCorrectAsValue()) stringValue = char + stringValue
    } while (lastIndex > 0 && char.isCorrectAsValue())

    stringValue = stringValue.trim()
    if (stringValue.isEmpty()) return null
    if (stringValue.contains('%') && !asPercent) return null
    if (!stringValue.contains('%') && asPercent) return null

    // If is by percent remove '%' then convert to Double
    return if (stringValue.contains('%'))
        stringValue.replace("%", "").trim().toDoubleOrNull()
    else stringValue.toDoubleOrNull()
}
