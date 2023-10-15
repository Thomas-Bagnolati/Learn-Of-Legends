package com.bagnolati.learnoflegends.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bagnolati.learnoflegends.core.model.Item


/*
 * Provides list of [Champion] for Composable previews.
 */
class ItemsPreviewParameterProvider : PreviewParameterProvider<List<Item>> {
    override val values: Sequence<List<Item>>
        get() = sequenceOf(
            listOf(
                PreviewParameterItems.draktharrItem.copy(id = 1),
                PreviewParameterItems.draktharrItem.copy(id = 2),
                PreviewParameterItems.draktharrItem.copy(id = 3),
                PreviewParameterItems.draktharrItem.copy(id = 4),
                PreviewParameterItems.draktharrItem.copy(id = 5),
            )
        )
}

private object PreviewParameterItems {

    val draktharrItem =
        Item(
            id = 6691,
            name = "Duskblade of Draktharr",
            plaintext = "Some play text",
            description =
            " <mainText><stats><attention>60</attention> Attack Damage<br><attention>18</attention> Lethality<br><attention>20</attention> Ability Haste</stats><br><li><passive>Nightstalker:</passive> Your Abilities deal up to an additional percent damage based on the target's missing health. When a champion that you have damaged within the last 3 seconds dies, you become <keywordStealth>Untargetable</keywordStealth> from non-structures for 1.5 seconds (30s ).<br><br><rarityMythic>Mythic Passive:</rarityMythic> Grants all other <rarityLegendary>Legendary</rarityLegendary> items Ability Haste and  Move Speed.<br></mainText><br>, image=Image(full=https://ddragon.leagueoflegends.com/cdn/13.19.1/img/item/6691.png), tags=[Damage, Stealth, CooldownReduction, Slow, NonbootsMovement, ArmorPenetration, AbilityHaste], colloq=, gold=Gold(base=900, purchasable=true, sell=2170, total=3100), stats=Stats(flatArmorMod=null, flatAttackSpeedMod=null, flatBlockMod=null, flatCritChanceMod=null, flatCritDamageMod=null, flatEXPBonus=null, flatEnergyPoolMod=null, flatEnergyRegenMod=null, flatHPPoolMod=null, flatHPRegenMod=null, flatMPPoolMod=null, flatMPRegenMod=null, flatMagicDamageMod=null, flatMovementSpeedMod=null, flatPhysicalDamageMod=60.0, flatSpellBlockMod=null, percentArmorMod=null, percentAttackSpeedMod=null, percentBlockMod=null, percentCritChanceMod=null, percentCritDamageMod=null, percentDodgeMod=null, percentEXPBonus=null, percentHPPoolMod=null, percentHPRegenMod=null, percentLifeStealMod=null, percentMPPoolMod=null, percentMPRegenMod=null, percentMagicDamageMod=null, percentMovementSpeedMod=null, percentPhysicalDamageMod=null, percentSpellBlockMod=null, percentSpellVampMod=null, rFlatArmorModPerLevel=null, rFlatArmorPenetrationMod=null, rFlatArmorPenetrationModPerLevel=null, rFlatCritChanceModPerLevel=null, rFlatCritDamageModPerLevel=null, rFlatDodgeMod=null, rFlatDodgeModPerLevel=null, rFlatEnergyModPerLevel=null, rFlatEnergyRegenModPerLevel=null, rFlatGoldPer10Mod=null, rFlatHPModPerLevel=null, rFlatHPRegenModPerLevel=null, rFlatMPModPerLevel=null, rFlatMPRegenModPerLevel=null, rFlatMagicDamageModPerLevel=null, rFlatMagicPenetrationMod=null, rFlatMagicPenetrationModPerLevel=null, rFlatMovementSpeedModPerLevel=null, rFlatPhysicalDamageModPerLevel=null, rFlatSpellBlockModPerLevel=null, rFlatTimeDeadMod=null, rFlatTimeDeadModPerLevel=null, rPercentArmorPenetrationMod=null, rPercentArmorPenetrationModPerLevel=null, rPercentAttackSpeedModPerLevel=null, rPercentCooldownMod=null, rPercentCooldownModPerLevel=null, rPercentMagicPenetrationMod=null, rPercentMagicPenetrationModPerLevel=null, rPercentMovementSpeedModPerLevel=null, rPercentTimeDeadMod=null, rPercentTimeDeadModPerLevel=null), from=[3134, 3133], into=[7002], category=MYTHIC, depth=3, maps=Maps(summonersRift=true, aram=true, nexusBlitz=true, teamFightTactics=false, arena=false)",
            image = Item.Image(full = null),
            tags = listOf("Damage", "Stealth", "CooldownReduction", "Slow", "NonbootsMovement", "ArmorPenetration", "AbilityHaste"),
            colloq = "",
            gold = Item.Gold(base = 900, purchasable = true, sell = 2170, total = 3100),
            stats = Item.Stats(
                flatPhysicalDamageMod = 60.0,
            ),
            from = listOf("3134", "3133"),
            into = listOf("7002"),
            category = Item.Category.MYTHIC,
            depth = 3,
            maps = Item.Maps(summonersRift = true, aram = true, nexusBlitz = true, teamFightTactics = false, arena = false),
        )
}