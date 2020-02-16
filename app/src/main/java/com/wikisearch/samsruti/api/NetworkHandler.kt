package com.wikisearch.samsruti.api

import android.util.Log
import com.wikisearch.samsruti.model.Page
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "PageBoundaryCallback"

fun searchWikiPageHelper(
    service: WikiApiService,
    query: String,
    onSuccess: (pages: List<Page>) -> Unit,
    onError: (error: String) -> Unit
) {

    service.searchWikiPage(
        action = "query",
        format = "json",
        prop = "pageimages|pageterms",
        generator = "prefixsearch" ,
        redirects = 1,
        formatVersion = 2,
        pictureType = "thumbnail",
        pictureThumbSize = 50,
        pilimit = 10 ,
        wbptTerms = "description",
        gpsSearchQuery = query,
        gpsLimit = 10
    ).enqueue(
        object : Callback<NetworkResponse> {
            override fun onFailure(call: Call<NetworkResponse>?, t: Throwable) {
                Log.d(TAG, "fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<NetworkResponse>?,
                response: Response<NetworkResponse>
            ) {
//                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val pages = response.body()?.query?.pages ?: emptyList()
                    Log.d(TAG, "got pages $pages")
                    onSuccess(pages)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
    )
}
