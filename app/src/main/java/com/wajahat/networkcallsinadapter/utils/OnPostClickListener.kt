package com.wajahat.networkcallsinadapter.utils

import com.wajahat.networkcallsinadapter.data.model.Post

interface OnPostClickListener {

    fun onPostClicked(post: Post)
}