
package com.wikisearch.samsruti.repository

import android.util.Log
import androidx.paging.LivePagedListBuilder
import com.wikisearch.samsruti.api.WikiApiService
import com.wikisearch.samsruti.db.WikiLocalCache
import com.wikisearch.samsruti.model.PageSearchResult

/**
 * WikiRepository class that works with local and remote data sources.
 */
class WikiRepository(
    private val service: WikiApiService,
    private val cache: WikiLocalCache
) {

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): PageSearchResult {
        Log.d("WikiRepository", "New query: $query")

        val dataSourceFactory = cache.pagesByTitle(query)

        val boundaryCallback = PageBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        return PageSearchResult(data, networkErrors)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 10
    }
}
