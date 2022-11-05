package com.wajahat.networkcallsinadapter.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostsApi {

    val postsService: PostsService by lazy { getRetrofit().create(PostsService::class.java) }
    // Using different url from the original due to confidentiality
    const val BASE_URL = "https://public-api.wordpress.com/rest/v1.1/sites/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClientBuilder())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** Intercept the requests to catch the logs
     * @return OkHttpClient Http Client used by the Retrofit
     * */
    private fun getHttpClientBuilder(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }
}