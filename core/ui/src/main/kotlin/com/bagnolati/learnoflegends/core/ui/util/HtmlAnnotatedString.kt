package com.bagnolati.learnoflegends.core.ui.util

import android.graphics.Typeface
import android.text.Html
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration


/**
 * Transform [String] html from Lol to [AnnotatedString] with
 * highlight and parsing.
 */
fun String.lolHtmlToAnnotatedString(): AnnotatedString = buildAnnotatedString {
    var text = this@lolHtmlToAnnotatedString
    val spanned = Html.fromHtml(this@lolHtmlToAnnotatedString, Html.FROM_HTML_MODE_LEGACY)
    // represent all text inside a tag
    val contents = arrayListOf<Content>()

    HtmlTag.values().forEach { tag ->
        when (tag) {
            HtmlTag.BR -> {}
            HtmlTag.LI -> {}
            HtmlTag.MAIN_TEXT -> {}
            HtmlTag.STATS -> {}

            else -> {
                val indexes: ArrayList<Indexes> = text.getAllIndexesFromTag(tag)
                indexes.forEach { index ->
                    // For all indexes add value inside content variable.
                    contents.add(
                        Content(
                            text = text
                                .substring(index.start, index.end)
                                .removeHtmlTag(tag),
                            tag = tag,
                            position = index.position
                        )
                    )
                }
                Log.d("CHECK", "content=$contents")
            }

        }
    }

    // Parse Html to String (it will delete all tags and replace it with indent html)
    text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString()


    var lastContent: Content? = null
    var lastStartIndex = 0
    var count = 0
    // Play with each content to apply some style like color.
    contents.forEach { content ->

        val body = content.text
        val tag = content.tag

        val start = text.indexOf(body, lastStartIndex, true)
        val end = start + body.count()

        if (count == content.position)
            addStyle(
                style = SpanStyle(color = tag.color),
                start = start,
                end = end
            )
//        Log.d("CHECK", "StartEnd=$start / $end")

        val isSame = lastContent == content

        lastStartIndex = if (isSame) end else 0
        lastContent = content
        count++
    }
    append(text)
}

fun String.removeHtmlTag(tag: HtmlTag): String {
    return replace(tag.getStartTag(), "", true)
        .replace(tag.getEndTag(), "", true)
}

/**
 * For all same tag get first and last index of it content.
 * For "<active>content</active>" case index of '<' and last index of '>'
 * will be return as [Pair].
 *
 * @param tag is the tag to retrieve.
 *
 * @return an [ArrayList] of each indexes.
 */
fun String.getAllIndexesFromTag(
    tag: HtmlTag
): ArrayList<Indexes> {
    val indexes = arrayListOf<Indexes>()

    this.indexesInsideStrings(
        start = tag.getStartTag(),
        end = tag.getEndTag(),
        doOnEachIndexes = indexes::add
    )

    return indexes
}

/**
 * Do on each indexes inside 2 strings.
 * For <active>content</active> index of '<' and '>' arent used
 */
private fun String.indexesInsideStrings(
    start: String,
    end: String,
    doOnEachIndexes: (Indexes) -> Unit,
) {
    // One time it find a tag, restart search from last index
    var lastEndIndex = 0
    var position = 0

    // search indexes of tags,
    // repeat while is new occurrence.
    do {
        // Find indexes
        val startIndex = this.indexOf(string = start, startIndex = lastEndIndex)
        val endIndex = this.indexOf(string = end, startIndex = startIndex) + end.count()

        // Add indexes on array
        if (startIndex != -1)
            doOnEachIndexes(
                Indexes(startIndex, endIndex, position)
            )

        // update lastIndex for next search
        lastEndIndex = endIndex

        // increment position
        position++
    } while (startIndex != -1 && startIndex < endIndex)
}


/**
 * Get all indexes of a String
 * @param string The string to find to retrieve indexes.
 */
private fun String.indexesOf(
    string: String,
    predicate: (Boolean) -> Unit,
    doOnEachIndexes: (Pair<Int, Int>) -> Unit,
) {
    // One time it find a tag, restart search from last index
    var lastEndIndex = 0

    // search indexes of tags,
    // repeat while is new occurrence.
    do {
        // Find indexes
        val startIndex = this.indexOf(string = string, startIndex = lastEndIndex)
        val endIndex = startIndex + string.count()

        // Add indexes on array
        if (startIndex != -1)
            doOnEachIndexes(Pair(startIndex, endIndex))

        // update lastIndex for next search
        lastEndIndex = endIndex

    } while (startIndex != -1 && startIndex < endIndex)
}


fun HtmlTag.getStartTag(): String = "<$tagName>"
fun HtmlTag.getEndTag(): String = "</$tagName>"


data class Content(
    val text: String,
    val tag: HtmlTag,
    val position: Int
)

data class Indexes(
    val start: Int,
    val end: Int,
    val position: Int
)

enum class HtmlTag(
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

/**
 * Converts a [Spanned] into an [AnnotatedString] trying to keep as much formatting as possible.
 *
 * Currently supports `bold`, `italic`, `underline` and `color`.
 */
fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), start, end)
            }

            is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
            is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
        }
    }
}