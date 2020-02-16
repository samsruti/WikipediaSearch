package com.wikisearch.samsruti.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WikiApiService {
    @GET("/w/api.php")
    fun searchWikiPage(
        @Query("action") action: String,
        @Query("format") format: String,
        @Query("prop") prop: String,
        @Query("generator") generator: String,
        @Query("redirects") redirects: Int,
        @Query("formatversion") formatVersion: Int,
        @Query("piprop") pictureType: String,
        @Query("pithumbsize") pictureThumbSize: Int,
        @Query("pilimit") pilimit: Int,

        @Query("wbptterms") wbptTerms: String,
        @Query("gpssearch") gpsSearchQuery: String,
        @Query("gpslimit") gpsLimit: Int
    ): Call<NetworkResponse>

    companion object {

        private const val BASE_URL = "https://en.wikipedia.org/"


        fun create(): WikiApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WikiApiService::class.java)
        }
    }
}
