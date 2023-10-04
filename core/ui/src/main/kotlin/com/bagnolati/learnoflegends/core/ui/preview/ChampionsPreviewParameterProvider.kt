package com.bagnolati.learnoflegends.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bagnolati.learnoflegends.core.model.Champion
import com.bagnolati.learnoflegends.core.ui.preview.PreviewParameterData.ahriChampionPreview
import com.bagnolati.learnoflegends.core.ui.preview.PreviewParameterData.qiyanaChampionPreview


/*
 * Provides list of [Champion] for Composable previews.
 */
class ChampionsPreviewParameterProvider : PreviewParameterProvider<List<Champion>> {
    override val values: Sequence<List<Champion>>
        get() = sequenceOf(
            listOf(
                ahriChampionPreview,
                qiyanaChampionPreview,
            )
        )
}

internal object PreviewParameterData {
    /**
     * Fake data of a [Champion.ImageUrl] for preview
     */
    private val EmptyChampionImage = Champion.ImageUrl("", "", "", "", "")

    /**
     * Fake data of a [Champion.Spell] for preview
     */
    private val ahriQSpellPreview = Champion.Spell(
        id = "AhriQ",
        name = "Orb of Deception",
        description = "Ahri sends out and pulls back her orb, dealing magic damage on the way out and true damage on the way back. ",
        tooltip = "Ahri throws then pulls back her orb, dealing <magicDamage>{{ totaldamage }} magic damage</magicDamage> on the way out and <trueDamage>{{ totaldamage }} true damage</trueDamage> on the way back.{{ spellmodifierdescriptionappend }}",
        levelTip = Champion.Spell.LevelTip(
            label = listOf(
                "@AbilityResourceName@ Cost",
                "Damage"
            ),
            effect = listOf(
                "{{ cost }} -> {{ costNL }}",
                "{{ basedamage }} -> {{ basedamageNL }}"
            )
        ),
        maxRank = 5,
        coolDown = listOf(7f, 7f, 7f, 7f, 7f),
        coolDownBurn = "7",
        cost = listOf(55, 65, 75, 85, 95),
        costBurn = "55/65/75/85/95",
        effect = listOf(
            null,
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
            listOf(0f, 0f, 0f, 0f, 0f),
        ),
        effectBurn = listOf(null, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"),
        costType = " {{ abilityresourcename }}",
        maxammo = "-1",
        range = listOf(970, 970, 970, 970, 970),
        rangeBurn = "970",
        image = EmptyChampionImage,
        resource = "{{ cost }} {{ abilityresourcename }}"
    )

    /**
     * Fake data of a [Champion.Passive] for preview
     */
    private val passivePreview = Champion.Passive(
        name = "Essence Theft",
        description = "After killing 9 minions or monsters, Ahri heals.<br>After taking down an enemy champion, Ahri heals for a greater amount.",
        imageUrl = EmptyChampionImage
    )

    /**
     * Fake data of a [Champion.Stats] for preview
     */
    private val championStatPreview = Champion.Stats(
        hp = 590,
        hpPerLevel = 124,
        mp = 320.0,
        mpPerLevel = 50.0,
        moveSpeed = 335,
        armor = 28,
        armorPerLevel = 4.7,
        spellBlock = 32,
        spellBlockPerLevel = 2.05,
        attackRange = 150,
        hpRegen = 6.0,
        hpRegenPerLevel = 0.9,
        mpRegen = 8.0,
        mpRegenPerLevel = 0.7,
        crit = 0,
        critPerLevel = 0,
        attackDamage = 66,
        attackDamagePerLevel = 3.1,
        attackSpeedPerLevel = 2.1,
        attackSpeed = 0.68,
    )

    val qiyanaChampionPreview = Champion(
        id = "Qiyana",
        name = "Qiyana",
        title = "Empress of the Elements",
        imageUrl = EmptyChampionImage,
        stats = championStatPreview,
    )

    val ahriChampionPreview = Champion(
        id = "ahri",
        name = "Ahri",
        title = "the Nine-Tailed Fox",
        imageUrl = EmptyChampionImage,
        stats = championStatPreview,
        passive = passivePreview,
        spells = listOf(ahriQSpellPreview),
        tags = listOf(
            Champion.Tag("Mage", "")
        )
    )


}