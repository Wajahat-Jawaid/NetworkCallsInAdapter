package com.wajahat.networkcallsinadapter.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.DateFormat
import java.util.*

object PostUtils {

    fun printDate(date: Date): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormat.format(date)
    }

    /** Extension function that returns the [Spanned] object based on API level */
    fun String.formatHtml(): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY);
        } else {
            Html.fromHtml(this);
        }
    }
}