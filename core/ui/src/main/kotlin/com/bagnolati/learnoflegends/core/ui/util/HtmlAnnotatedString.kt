package com.bagnolati.learnoflegends.core.ui.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString


fun String.toHtmlAnnotatedString(): AnnotatedString = buildAnnotatedString {
    var text = this@toHtmlAnnotatedString

    HtmlTags.values().forEach { tag ->

        when (tag) {
            HtmlTags.BR -> text = text.replace(tag.start(), "\n", true)
            HtmlTags.LI -> text = text.replace(tag.start(), "\n \u2022 ", true)
            HtmlTags.MAIN_TEXT -> text = text.removeHtmlTag(tag)
            HtmlTags.STATS -> text = text.removeHtmlTag(tag)

            else -> {
                val indexes: ArrayList<Pair<Int, Int>> = getAllIndexes(text, tag)
                Log.d("CHECK", "Indexes=$indexes")
                val content = arrayListOf<String>()
                indexes.forEach { pairIndex ->
                    content.add(text.substring(pairIndex.first, pairIndex.second))
                    addStyle(
                        style = SpanStyle(color = tag.color),
                        start = pairIndex.first,
                        end = pairIndex.second
                    )
                }
                Log.d("CHECK", "Content=$content")
            }

        }

    }
    append(text)
}

fun String.removeHtmlTag(tag: HtmlTags): String {
    return replace(tag.start(), "", true)
        .replace(tag.end(), "", true)
}

private fun getAllIndexes(
    text: String,
    tag: HtmlTags
): ArrayList<Pair<Int, Int>> {
    val indexes = arrayListOf<Pair<Int, Int>>()
    var lastEndIndex = 0

    // repeat while is new occurrence
    do {
        val startTag = text.indexOf(string = tag.start(), startIndex = lastEndIndex)
        val endTag = text.indexOf(string = tag.end(), startIndex = startTag) + tag.end().count()

        lastEndIndex = endTag
        if (startTag != -1)
            indexes.add(Pair(startTag, endTag))

    } while (startTag != -1 && startTag < endTag)

    return indexes
}


fun HtmlTags.start(): String = "<$tagName>"
fun HtmlTags.end(): String = "</$tagName>"

enum class HtmlTags(
    val tagName: String,
    val color: Color = Color.White
) {
    BR("br"),
    MAIN_TEXT("mainText"),
    LI("li"),

    STATS("stats", Color.Red),
    ATTENTION("attention", Color.Yellow),
    SCALE_AD("scaleAD", Color.Red),
    SCALE_AP("scaleAP", Color.Red),
    SCALE_MANA("scaleMana", Color.Blue),
    MAGIC_DAMAGE("magicDamage", Color.Magenta),
    ON_HIT("onHit", Color.Blue),
    PASSIVE("passive", Color.Blue),
    ORNN_BONUS("ornnBonus", Color.Magenta),
    RARITY_LEGENDARY("rarityLegendary", Color.Green),
    RARITY_MYTHIC("rarityMythic", Color.Green),
    KEYWORD_STEALTH("keywordStealth", Color.Cyan),
    RULES("rules", Color.Gray),
    STATUS("status", Color.LightGray),
    ACTIVE("active", Color.Magenta)

}
