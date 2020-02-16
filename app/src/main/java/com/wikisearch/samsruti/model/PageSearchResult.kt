package com.wikisearch.samsruti.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class PageSearchResult(
    val data: LiveData<PagedList<Page>>,
    val networkError: LiveData<String>
)

