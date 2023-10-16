package com.bagnolati.learnoflegends.core.ui.util

import android.text.Html
import androidx.core.text.toSpanned

/**
 * Decode HTML String to Text
 */
fun String.htmlToString(): String =
    Html.fromHtml(
        this,
         Html.FROM_HTML_MODE_LEGACY,
    ).toString()

