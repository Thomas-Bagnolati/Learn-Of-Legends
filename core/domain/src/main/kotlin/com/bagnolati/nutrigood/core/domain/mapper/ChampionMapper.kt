package com.bagnolati.nutrigood.core.domain.mapper

import android.text.Html
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.network.DdragonUrl
import com.bagnolati.learnoflegends.core.network.LeagueOfLegendsUrl
import com.bagnolati.learnoflegends.core.network.model.NetworkChampion
import java.util.Locale


/**
 * Map list Of [NetworkChampion] to list of [Champion]
 */
fun List<NetworkChampion>.asChampions(): List<Champion> =
    mapIndexed { index, networkChampion ->
        networkChampion.asChampion(index)
    }

/**
 * Map [NetworkChampion] to [Champion]
 */
fun NetworkChampion.asChampion(index: Int? = null): Champion {
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
        stats = stats.asStats(index ?: 0),
        passive = passive?.asPassive(),
        spells = spells.map { it.asSpell() },
        skins = skins.map { it.asSkin(full = image.full) }
    )
}

/**
 * Map String tags network to [Champion.Tag]
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
 *
 * All stats as Double to easily can play with it.
 * @param index to retrieve alphabetic position.
 */
fun NetworkChampion.NetworkStats.asStats(index: Int): Champion.Stats {
    return Champion.Stats(
        alphabetic = (index + 1).toDouble(),
        hp = hp.toDouble(),
        hpPerLevel = hpperlevel.toDouble(),
        mp = mp,
        mpPerLevel = mpperlevel,
        moveSpeed = movespeed.toDouble(),
        armor = armor.toDouble(),
        armorPerLevel = armorperlevel,
        spellBlock = spellblock.toDouble(),
        spellBlockPerLevel = spellblockperlevel,
        attackRange = attackrange.toDouble(),
        hpRegen = hpregen,
        hpRegenPerLevel = hpregenperlevel,
        mpRegen = mpregen,
        mpRegenPerLevel = mpregenperlevel,
        crit = crit.toDouble(),
        critPerLevel = critperlevel.toDouble(),
        attackDamage = attackdamage.toDouble(),
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
 * Map [NetworkChampion.NetworkSkin] network to [Champion.Skin]
 */
fun NetworkChampion.NetworkSkin.asSkin(full: String): Champion.Skin {
    val champWithoutExt = full.removeSuffix(".png")
    val ext = ".jpg"
    val champUrl = champWithoutExt + "_" + num + ext

    return Champion.Skin(
        id = id,
        num = num,
        name = name,
        chromas = chromas,
        splashUrl = DdragonUrl.SPLASH_IMAGE_URL + champUrl
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
fun String.capitalize() = this.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
