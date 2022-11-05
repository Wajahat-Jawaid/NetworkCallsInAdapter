package com.wajahat.networkcallsinadapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.wajahat.networkcallsinadapter.PostsDataProvider.getDummyPosts
import com.wajahat.networkcallsinadapter.data.api.PostsService
import com.wajahat.networkcallsinadapter.data.model.response.PostsResponse
import com.wajahat.networkcallsinadapter.data.repository.PostsRepository
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class PostsApiTest {

    private lateinit var repository: PostsRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var gson: Gson

    @Mock
    lateinit var postsService: PostsService

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = PostsRepository()
        gson = Gson()
        mockWebServer = MockWebServer()
        postsService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(PostsService::class.java)
    }

    @Test
    fun `get all posts api test`() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody(Gson().toJson(getDummyPostsResponse())))
            val response = postsService.getPosts(2)
            val request = mockWebServer.takeRequest()
            assertNotEquals("/discover.wordpress.com/posts?number=10", request.path)
            assertEquals("/discover.wordpress.com/posts?number=2", request.path)
            assertEquals(true, response.posts?.isNotEmpty() == true)
        }
    }

    private fun getDummyPostsResponse(): PostsResponse {
        return PostsResponse(getDummyPosts())
    }
}