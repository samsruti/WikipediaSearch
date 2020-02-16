package com.wikisearch.samsruti.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wikisearch.samsruti.model.Page
import com.wikisearch.samsruti.model.PageSearchResult
import com.wikisearch.samsruti.repository.WikiRepository

class WikiPagesViewModel(private val repository: WikiRepository) : ViewModel() {
    private val queryLiveData = MutableLiveData<String>()
    private val pageSearchResult: LiveData<PageSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }

    val pages: LiveData<PagedList<Page>> = Transformations.switchMap(pageSearchResult) { it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(pageSearchResult) {
        it.networkError
    }

    private val _navigateToSelectedPage= MutableLiveData<Page>()

    val navigateToSelectedPage: LiveData<Page>
        get() = _navigateToSelectedPage



    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun displayPageDetails(page: Page) {
        _navigateToSelectedPage.value = page
    }

    fun displayPageCompleted() {
        _navigateToSelectedPage.value = null
    }

    fun clearAllData() {
        queryLiveData.postValue("")
    }

}
