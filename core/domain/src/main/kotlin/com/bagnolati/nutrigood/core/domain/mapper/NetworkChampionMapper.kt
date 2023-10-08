package com.bagnolati.nutrigood.core.domain.mapper

import android.text.Html
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.network.DdragonUrl
import com.bagnolati.learnoflegends.core.network.LeagueOfLegendsUrl
import com.bagnolati.learnoflegends.core.network.model.NetworkChampion
import java.util.Locale


/**
 * Map [NetworkChampion] to [Champion]
 */
fun NetworkChampion.asChampion(): Champion {
    return Champion(
        id = id,
        name = name,
        title = title.capitalize(),
        blurbTrunked = blurb,
        blurbComplete = lore,
        allyTips = allytips,
        enemyTips = enemytips,
        tags = tags.map { it.asTag() },
        imageUrl = image.asImage(),
        stats = stats.asStats(),
        passive = passive?.asPassive(),
        spells = spells.map { it.asSpell() }
    )
}

/**
 * Map tag network to [Champion.Tag]
 */
fun String.asTag(): Champion.Tag {
    val tagImageUrl = LeagueOfLegendsUrl.ROLE_IMG + "role_icon_${this.lowercase()}.png"
    return Champion.Tag(
        tagName = this,
        tagImageUrl = tagImageUrl
    )
}


/**
 * Map [NetworkChampion.NetworkSpell] to [Champion.Spell]
 */
fun NetworkChampion.NetworkSpell.asSpell(): Champion.Spell {
    return Champion.Spell(
        id = id,
        name = name,
        description = description.htmlToString(),
        tooltip = tooltip,
        levelTip = Champion.Spell.LevelTip(label = leveltip?.label, effect = leveltip?.effect),
        maxRank = maxrank,
        coolDown = cooldown,
        coolDownBurn = cooldownBurn,
        cost = cost,
        costBurn = costBurn,
        effect = effect,
        effectBurn = effectBurn,
        costType = costType,
        maxammo = maxammo,
        range = range,
        rangeBurn = rangeBurn,
        image = image.asImage(),
        resource = resource
    )
}

/**
 * Map [NetworkChampion.NetworkPassive] to [Champion.Passive]
 */
fun NetworkChampion.NetworkPassive.asPassive(): Champion.Passive {
    return Champion.Passive(
        name = name,
        description = description.htmlToString(),
        imageUrl = image.asImage()
    )
}

/**
 * Map [NetworkChampion.NetworkStats] to [Champion.Stats]
 */
fun NetworkChampion.NetworkStats.asStats(): Champion.Stats {
    return Champion.Stats(
        hp = hp,
        hpPerLevel = hpperlevel,
        mp = mp,
        mpPerLevel = mpperlevel,
        moveSpeed = movespeed,
        armor = armor,
        armorPerLevel = armorperlevel,
        spellBlock = spellblock,
        spellBlockPerLevel = spellblockperlevel,
        attackRange = attackrange,
        hpRegen = hpregen,
        hpRegenPerLevel = hpregenperlevel,
        mpRegen = mpregen,
        mpRegenPerLevel = mpregenperlevel,
        crit = crit,
        critPerLevel = critperlevel,
        attackDamage = attackdamage,
        attackDamagePerLevel = attackdamageperlevel,
        attackSpeedPerLevel = attackspeedperlevel,
        attackSpeed = attackspeed,
    )
}

/**
 * Map [NetworkChampion.NetworkImage] to [Champion.ImageUrl]
 */
fun NetworkChampion.NetworkImage.asImage(): Champion.ImageUrl {
    val champWithoutExt = full.removeSuffix(".png")
    val ext = ".jpg"
    val champUrl = champWithoutExt + "_0" + ext

    return Champion.ImageUrl(
        passive = DdragonUrl.PASSIVE_IMAGE_URL + full,
        square = DdragonUrl.MINI_IMAGE_URL + full,
        splash = DdragonUrl.SPLASH_IMAGE_URL + champUrl,
        loading = DdragonUrl.LOADING_IMAGE_URL + champUrl,
        spell = DdragonUrl.SPELL_IMAGE_URL + full
    )
}


/**
 * Decode HTML String to Text
 */
fun String.htmlToString(): String =
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()

/**
 * Safely capitalize String.
 */
fun String.capitalize() = this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
