package com.wajahat.networkcallsinadapter.ui.post

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wajahat.networkcallsinadapter.R
import com.wajahat.networkcallsinadapter.data.model.Post
import com.wajahat.networkcallsinadapter.data.model.PostAdapterModel
import com.wajahat.networkcallsinadapter.data.model.PostHeader
import com.wajahat.networkcallsinadapter.data.model.response.PostsResponse
import com.wajahat.networkcallsinadapter.data.repository.PostsRepository
import com.wajahat.networkcallsinadapter.ui.base.ViewModelFactory
import com.wajahat.networkcallsinadapter.utils.ConnectionUtils.isNetworkAvailable
import com.wajahat.networkcallsinadapter.utils.OnPostClickListener
import com.wajahat.networkcallsinadapter.utils.PostUtils.printDate
import com.wajahat.networkcallsinadapter.utils.Resource
import com.wajahat.networkcallsinadapter.utils.Status
import com.wajahat.networkcallsinadapter.utils.VerticalItemDecoration

class PostsListFragment : Fragment(), OnPostClickListener {
    
    /**
     * Although in a practical world, we usually initialize the objects using some Dependency Injection framework
     * like Dagger|Hilt. But since, for the scope of this sample, I'm initializing it like below in #onCreate()
     */
    private lateinit var mViewModel: PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this, ViewModelFactory(PostsRepository()))[PostsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPosts()
    }

    private fun fetchPosts() {
        // Verify if Internet is present
        if (!isNetworkAvailable(requireContext())) {
            hideLoadingAnimation()
            showErrorView(R.string.no_internet)
            return
        }
        mViewModel.getPosts()
        mViewModel.postsResponse.observe(viewLifecycleOwner) { (status, data): Resource<PostsResponse?> ->
            if (status === Status.SUCCESS) {
                // If though API succeeded, but maybe no posts are present for now, then we need to display the
                // error message
                if (data?.posts == null) {
                    showErrorView(R.string.no_data_found)
                    return@observe
                }

                // Posts list fetched from the API
                val responsePosts = data.posts

                // Populating the generic list of #PostAdapterModel so as to handle the header views as well
                val formattedPosts: MutableList<PostAdapterModel> = ArrayList()

                // With every header supposed to be added to the #formattedPosts list, we must increase the loop
                // iterations limit.
                var formattedPostsSize = responsePosts.size

                // Since we are populating the #formattedPosts list whose size is always bigger than the original
                // list of posts i.e. #responsePosts because of the addition of header items too. Therefore, to access
                // a particular index/object in the #responsePosts, we must keep track of the current index using a
                // separate variable i.e. #postIndex
                var postIndex = 0
                var i = 1
                while (i <= formattedPostsSize) {
                    val previousPost = if (postIndex > 0) responsePosts[postIndex - 1] else null
                    val post = responsePosts[postIndex]
                    val isNewDay = previousPost == null || mViewModel.isNewDay(previousPost, post)

                    // If we get a new day, we must add a header item to map it onto the header view in #PostsAdapter
                    if (isNewDay) {
                        formattedPosts.add(PostHeader(printDate(post.getDateObj())))
                        // Increment the #formattedPostsSize due to the addition of a header
                        ++formattedPostsSize
                        // If the header is added, we must increment the 'i' again, one due to adding the header
                        // in #formattedPosts list and the other (3rd argument of the loop) due to adding the item
                        // in #formattedPosts list.
                        ++i
                    }
                    formattedPosts.add(post)
                    ++i
                    ++postIndex
                }
                hideLoadingAnimation()
                mapDataOnRecyclerView(formattedPosts)
            } else if (status === Status.ERROR) {
                // Tip: You can reproduce this issue by changing the URL to something wrong so that we're sure that
                // the error occurs
                hideLoadingAnimation()
                showErrorView(R.string.unexpected_error)
            }
        }
    }

    private fun hideLoadingAnimation() {
        requireView().findViewById<View>(R.id.animation_view).visibility = View.GONE
    }

    private fun mapDataOnRecyclerView(posts: List<PostAdapterModel>) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.addItemDecoration(VerticalItemDecoration(requireContext(), 1))
        recyclerView.adapter = PostsAdapter(posts, viewLifecycleOwner, mViewModel, this)
    }

    private fun showErrorView(@StringRes msg: Int) {
        val errorText = requireView().findViewById<TextView>(R.id.error_text)
        errorText.visibility = View.VISIBLE
        errorText.text = getString(msg)
    }

    override fun onPostClicked(post: Post) {
        val browseIntent = Intent()
        browseIntent.action = Intent.ACTION_VIEW
        browseIntent.data = post.getUri()

        // Verifying if the link can be opened
        try {
            requireActivity().startActivity(browseIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.no_application_to_open_link, Toast.LENGTH_SHORT).show()
        }
    }
}