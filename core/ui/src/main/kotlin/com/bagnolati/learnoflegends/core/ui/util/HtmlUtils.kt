package com.bagnolati.learnoflegends.core.ui.util

import android.graphics.Typeface
import android.text.Html
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.bagnolati.learnoflegends.core.model.HtmlTag
import com.bagnolati.learnoflegends.core.model.HtmlTag.ACTIVE
import com.bagnolati.learnoflegends.core.model.HtmlTag.ATTACK_SPEED
import com.bagnolati.learnoflegends.core.model.HtmlTag.ATTENTION
import com.bagnolati.learnoflegends.core.model.HtmlTag.BOLD
import com.bagnolati.learnoflegends.core.model.HtmlTag.BR
import com.bagnolati.learnoflegends.core.model.HtmlTag.BUFFED_STAT
import com.bagnolati.learnoflegends.core.model.HtmlTag.FLAVOR_TEXT
import com.bagnolati.learnoflegends.core.model.HtmlTag.HEALING
import com.bagnolati.learnoflegends.core.model.HtmlTag.ITALIC
import com.bagnolati.learnoflegends.core.model.HtmlTag.KEYWORD_MAJOR
import com.bagnolati.learnoflegends.core.model.HtmlTag.KEYWORD_STEALTH
import com.bagnolati.learnoflegends.core.model.HtmlTag.LI
import com.bagnolati.learnoflegends.core.model.HtmlTag.LIFE_STEAL
import com.bagnolati.learnoflegends.core.model.HtmlTag.MAGIC_DAMAGE
import com.bagnolati.learnoflegends.core.model.HtmlTag.MAIN_TEXT
import com.bagnolati.learnoflegends.core.model.HtmlTag.NERFED_STAT
import com.bagnolati.learnoflegends.core.model.HtmlTag.ON_HIT
import com.bagnolati.learnoflegends.core.model.HtmlTag.ORNN_BONUS
import com.bagnolati.learnoflegends.core.model.HtmlTag.PASSIVE
import com.bagnolati.learnoflegends.core.model.HtmlTag.PHYSICAL_DAMAGE
import com.bagnolati.learnoflegends.core.model.HtmlTag.RARITY_GENERIC
import com.bagnolati.learnoflegends.core.model.HtmlTag.RARITY_LEGENDARY
import com.bagnolati.learnoflegends.core.model.HtmlTag.RARITY_MYTHIC
import com.bagnolati.learnoflegends.core.model.HtmlTag.RULES
import com.bagnolati.learnoflegends.core.model.HtmlTag.SCALE_AD
import com.bagnolati.learnoflegends.core.model.HtmlTag.SCALE_AP
import com.bagnolati.learnoflegends.core.model.HtmlTag.SCALE_ARMOR
import com.bagnolati.learnoflegends.core.model.HtmlTag.SCALE_HEALTH
import com.bagnolati.learnoflegends.core.model.HtmlTag.SCALE_LETHALITY
import com.bagnolati.learnoflegends.core.model.HtmlTag.SCALE_MANA
import com.bagnolati.learnoflegends.core.model.HtmlTag.SCALE_MR
import com.bagnolati.learnoflegends.core.model.HtmlTag.SHIELD
import com.bagnolati.learnoflegends.core.model.HtmlTag.SPEED
import com.bagnolati.learnoflegends.core.model.HtmlTag.STATS
import com.bagnolati.learnoflegends.core.model.HtmlTag.STATUS
import com.bagnolati.learnoflegends.core.model.HtmlTag.TRUE_DAMAGE
import com.bagnolati.learnoflegends.core.model.HtmlTag.UNDERLINE
import com.bagnolati.learnoflegends.core.model.HtmlTag.UNIQUE
import com.bagnolati.learnoflegends.core.model.HtmlTag.values
import com.bagnolati.learnoflegends.core.model.closeTag
import com.bagnolati.learnoflegends.core.model.openTag
import com.bagnolati.learnoflegends.core.ui.theme.colorBlue
import com.bagnolati.learnoflegends.core.ui.theme.colorBrawn
import com.bagnolati.learnoflegends.core.ui.theme.colorDeepOrange
import com.bagnolati.learnoflegends.core.ui.theme.colorGreen
import com.bagnolati.learnoflegends.core.ui.theme.colorGrey
import com.bagnolati.learnoflegends.core.ui.theme.colorIndigo
import com.bagnolati.learnoflegends.core.ui.theme.colorLightGreen
import com.bagnolati.learnoflegends.core.ui.theme.colorLime
import com.bagnolati.learnoflegends.core.ui.theme.colorOrange
import com.bagnolati.learnoflegends.core.ui.theme.colorPink
import com.bagnolati.learnoflegends.core.ui.theme.colorPurple
import com.bagnolati.learnoflegends.core.ui.theme.colorRed
import com.bagnolati.learnoflegends.core.ui.theme.colorYellow

/**
 * Transform a [String] HTML to [AnnotatedString] and will also add style.
 *
 * @return [AnnotatedString] adding style on it.
 */
@Composable
fun String.fromHtmlToAnnotatedString(): AnnotatedString {
    var text = this@fromHtmlToAnnotatedString

    values().forEach { tag ->
        var addBold = false
        var addItalic = false
        var addUnderLine = false

        when (tag) {
            MAIN_TEXT -> text = text.removeTags(tag)
            STATS -> text = text.removeTagsAndContent(tag)
            ATTENTION -> addBold = true
            ORNN_BONUS -> addBold = true
            RULES -> addItalic = true
            LI -> text = text.replace(tag.openTag(), "<br> &#8226; ")
            PASSIVE -> {
                addUnderLine = true
                addBold = true
            }

            else -> {}
        }

        val color = when (tag) {
            FLAVOR_TEXT -> colorIndigo
            ORNN_BONUS -> colorOrange
            ACTIVE -> colorOrange
            KEYWORD_MAJOR -> colorPink
            KEYWORD_STEALTH -> colorPink
            RULES -> colorGrey
            NERFED_STAT -> colorGrey
            BUFFED_STAT -> colorLightGreen
            STATUS -> colorBlue
            UNIQUE -> colorYellow
            PHYSICAL_DAMAGE -> colorRed
            MAGIC_DAMAGE -> colorPurple
            HEALING -> colorGreen
            ATTACK_SPEED -> colorYellow
            LIFE_STEAL -> colorGreen
            ON_HIT -> colorLime
            SHIELD -> colorDeepOrange
            SPEED -> colorLime
            TRUE_DAMAGE -> Color.White
            SCALE_AD -> colorRed
            SCALE_AP -> colorPurple
            SCALE_ARMOR -> colorBrawn
            SCALE_MR -> colorIndigo
            SCALE_HEALTH -> colorGreen
            SCALE_MANA -> colorBlue
            SCALE_LETHALITY -> colorRed
            RARITY_GENERIC -> colorGreen
            RARITY_LEGENDARY -> colorOrange
            RARITY_MYTHIC -> colorYellow
            else -> null
        }

        if (addBold) text = text.addTagInsideTag(tag, BOLD)
        if (addItalic) text = text.addTagInsideTag(tag, ITALIC)
        if (addUnderLine) text = text.addTagInsideTag(tag, UNDERLINE)
        if (color != null) text = text.addColorInsideTag(tag, color)
    }

    text = text.trimBrTags()
//    return buildAnnotatedString { append(text) }
    return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toAnnotatedString()
}

private fun String.addColorInsideTag(tag: HtmlTag, color: Color?): String {
    color?.let {
        return this.replace(tag.openTag(), tag.openTag() + "<font color=${it.toArgb()}>")
            .replace(tag.closeTag(), "</font>" + tag.closeTag())
    }
    return this
}

private fun String.addTagInsideTag(tag: HtmlTag, tagToAdd: HtmlTag): String =
    this.replace(tag.openTag(), tag.openTag() + tagToAdd.openTag())
        .replace(tag.closeTag(), tag.closeTag() + tagToAdd.closeTag())


private fun String.removeTags(tag: HtmlTag): String =
    this.replace(tag.openTag(), "")
        .replace(tag.closeTag(), "")


private fun String.removeTagsAndContent(tag: HtmlTag): String {
    val start = indexOf(tag.openTag(), 0)
    val end = indexOf(tag.closeTag(), start) + tag.closeTag().count()
    if (start >= 0 && start < end) return this.removeRange(start, end)
    else return this
}

private fun String.trimBrTags(): String {
    var text = trim()

    while (text.indexOf(BR.openTag()) == 0) {
        text = text.removePrefix(BR.openTag()).trim()
    }
    while (text.lastIndexOf(BR.openTag()) + BR.openTag().count() == text.count()) {
        text = text.removeSuffix(BR.openTag()).trim()
    }
    return text
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