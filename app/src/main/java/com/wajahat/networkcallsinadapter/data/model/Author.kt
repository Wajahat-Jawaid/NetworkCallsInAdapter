package com.wajahat.networkcallsinadapter.data.model

import android.net.Uri

data class Author(val name: String, private val URL: String) {

    fun getUrl(): Uri {
        return Uri.parse(URL)
    }
}