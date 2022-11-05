package com.wajahat.networkcallsinadapter.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import com.wajahat.networkcallsinadapter.R
import com.wajahat.networkcallsinadapter.data.model.Post
import com.wajahat.networkcallsinadapter.data.model.Post.Companion.INVALID_COUNT
import com.wajahat.networkcallsinadapter.data.model.PostAdapterModel
import com.wajahat.networkcallsinadapter.data.model.PostHeader
import com.wajahat.networkcallsinadapter.utils.OnPostClickListener
import com.wajahat.networkcallsinadapter.utils.PostUtils.formatHtml
import com.wajahat.networkcallsinadapter.utils.Status

class PostsAdapter(
    private val posts: List<PostAdapterModel>,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: PostsViewModel,
    private val clickListener: OnPostClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // Deciding whether to inflate the header view or the item view based on the #viewType
        return if (viewType == PostAdapterModel.TYPE_ITEM)
            ItemViewHolder(inflater.inflate(R.layout.post_list_fragment_item, parent, false))
        else HeaderViewHolder(inflater.inflate(R.layout.post_list_fragment_header, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (posts[position].type == PostAdapterModel.TYPE_ITEM)
            (holder as ItemViewHolder).bind(position)
        else
            (holder as HeaderViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun getItemViewType(position: Int): Int {
        return posts[position].type
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val item = posts[position] as Post
            itemView.apply {
                Glide.with(context)
                    .load(item.featuredImage)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(findViewById<ShapeableImageView>(R.id.featured_image))
                findViewById<TextView>(R.id.title).text = item.title
                findViewById<TextView>(R.id.summary).text = item.excerpt.formatHtml()
                findViewById<TextView>(R.id.author_name).text = item.author.name

                // Since whenever the view comes on foreground as the result of scrolling up/down, everytime this
                // bind function gets called. If not placed the following condition, we will end up calling the API
                // everytime this view comes on foreground.
                // To avoid this repeated calls, we keep reference of the subscribers count in the [Post] model and
                // check if we already have set the value for this particular post. This way, we ensure that for a
                // particular post, repeated call to fetch the subscribers count is never made.
                if (item.subscribersCount == INVALID_COUNT) {
                    item.author.getUrl().host?.let { host ->
                        getSubscribersCount(itemView, item, host)
                    }
                } else {
                    // If the subscribers count is already fetched, set from the variable rather than setting from API
                    findViewById<TextView>(R.id.subscribers_count).text = item.subscribersCount.toString()
                }

                setOnClickListener {
                    clickListener.onPostClicked(item)
                }
            }
        }

        private fun getSubscribersCount(itemView: View, post: Post, blogUrl: String) {
            viewModel.getSubscribersCount(blogUrl).observe(viewLifecycleOwner) { response ->
                if (response.status == Status.SUCCESS) {
                    response.data?.subscribersCount?.let { count ->
                        post.subscribersCount = count
                        itemView.findViewById<TextView>(R.id.subscribers_count).text = count.toString()
                    }
                }
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val item = posts[position] as PostHeader
            itemView.apply {
                findViewById<TextView>(R.id.title).text = item.title
            }
        }
    }
}