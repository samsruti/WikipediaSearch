package com.wikisearch.samsruti.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.wikisearch.samsruti.api.WikiApiService
import com.wikisearch.samsruti.api.searchWikiPageHelper
import com.wikisearch.samsruti.db.WikiLocalCache
import com.wikisearch.samsruti.model.Page


class PageBoundaryCallback(
    private val query: String,
    private val service: WikiApiService,
    private val cache: WikiLocalCache
) : PagedList.BoundaryCallback<Page>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors


    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("PageBoundaryCallback", "onZeroItemsLoaded")
        requestAndSaveData(query)
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Page) {
        Log.d("PageBoundaryCallback", "onItemAtEndLoaded")
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
//        Todo make the list count - use the pagination
//        if (isRequestInProgress) return

//        isRequestInProgress = true
        searchWikiPageHelper(
            service = service,
            query = query,
            onSuccess = { pages ->
                cache.insert(pages){
//                    isRequestInProgress = false
                }
            },
            onError=  { error ->
            _networkErrors.postValue(error)
//            isRequestInProgress = false
        })
    }
}