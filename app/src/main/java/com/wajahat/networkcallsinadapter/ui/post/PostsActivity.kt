package com.wajahat.networkcallsinadapter.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wajahat.networkcallsinadapter.R

class PostsActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts_activity)
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, PostsListFragment())
            .commit()
    }
}