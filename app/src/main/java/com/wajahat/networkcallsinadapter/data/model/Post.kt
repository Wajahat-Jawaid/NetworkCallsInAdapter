package com.wajahat.networkcallsinadapter.data.model

import android.annotation.SuppressLint
import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class Post(
    val title: String,
    val excerpt: String,
    val author: Author,
    val date: String,
    val URL: String,
    @SerializedName("featured_image") val featuredImage: String,
    var _type: Int // Backing property for type
) : PostAdapterModel() {

    override var type: Int
        get() = TYPE_ITEM
        set(value) {
            _type = value
        }

    // Property to persist the subscribersCount fetched from the different API to avoid loading it again and again
    var subscribersCount: Int = INVALID_COUNT
        get() = if (field > 0) field else {
            field = INVALID_COUNT
            field
        }

    fun getUri(): Uri {
        return Uri.parse(URL)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateObj(): Date {
        return try {
            // Regarding Test cases, on some machines/OS, this date format might me unable to be parsed.
            // Please replace 'Z' in the end with 'X'. Reason that, 'Z' is still provided is, 'X' requires
            // minimum API level 24 to work whereas our app's min API level is 17.
            // So when unit testing, please just replace 'Z' with 'X'
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            dateFormat.parse(date)
        } catch (e: ParseException) {
            Date()
        }
    }

    companion object {
        // Just a random int that is absolutely not possible practically
        const val INVALID_COUNT = -100
    }
}