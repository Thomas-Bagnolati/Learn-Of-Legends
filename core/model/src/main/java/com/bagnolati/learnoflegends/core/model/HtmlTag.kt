package com.bagnolati.learnoflegends.core.model


/**
 * List of Html tags present on Ddragon description.
 */
enum class HtmlTag(
    val tagName: String,
) {
    BOLD(tagName = "b"),
    ITALIC(tagName = "i"),
    UNDERLINE(tagName = "u"),
    BR(tagName = "br"),
    LI(tagName = "i"),
    MAIN_TEXT(tagName = "mainText"),
    FLAVOR_TEXT(tagName = "flavorText"),

    STATS(tagName = "stats"),
    ATTENTION(tagName = "attention"),
    ORNN_BONUS(tagName = "ornnBonus"),
    ACTIVE(tagName = "active"),

    KEYWORD_MAJOR(tagName = "keywordMajor"),
    KEYWORD_STEALTH(tagName = "keywordStealth"),

    NERFED_STAT(tagName = "nerfedStat"),
    BUFFED_STAT(tagName = "buffedStat"),

    RULES(tagName = "rules"),
    STATUS(tagName = "status"),
    UNIQUE(tagName = "unique"),

    PHYSICAL_DAMAGE(tagName = "physicalDamage"),
    MAGIC_DAMAGE(tagName = "magicDamage"),

    HEALING(tagName = "healing"),
    ATTACK_SPEED(tagName = "attackSpeed"),
    LIFE_STEAL(tagName = "lifeSteal"),
    ON_HIT(tagName = "onHit"),
    PASSIVE(tagName = "passive"),
    SHIELD(tagName = "shield"),
    SPEED(tagName = "speed"),
    TRUE_DAMAGE(tagName = "trueDamage"),

    SCALE_AD(tagName = "scaleAD"),
    SCALE_AP(tagName = "scaleAP"),
    SCALE_ARMOR(tagName = "scaleArmor"),
    SCALE_MR(tagName = "scaleMR"),
    SCALE_HEALTH(tagName = "scaleHealth"),
    SCALE_MANA(tagName = "scaleMana"),
    SCALE_LETHALITY(tagName = "scaleLethality"),

    RARITY_GENERIC(tagName = "rarityGeneric"),
    RARITY_LEGENDARY(tagName = "rarityLegendary"),
    RARITY_MYTHIC(tagName = "rarityMythic");

}

fun HtmlTag.openTag(): String = "<$tagName>"
fun HtmlTag.closeTag(): String = "</$tagName>"
