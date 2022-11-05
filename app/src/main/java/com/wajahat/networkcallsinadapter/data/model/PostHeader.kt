package com.wajahat.networkcallsinadapter.data.model

/**
 * Model class to handle the headers in the Posts list screen
 * */
data class PostHeader(val title: String) : PostAdapterModel() {

    override var type: Int = TYPE_HEADER
}