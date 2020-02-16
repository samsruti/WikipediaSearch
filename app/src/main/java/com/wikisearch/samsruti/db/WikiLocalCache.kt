package com.wikisearch.samsruti.db

import android.util.Log
import androidx.paging.DataSource
import com.wikisearch.samsruti.model.Page
import java.util.concurrent.Executor

class WikiLocalCache(
        private val repoDao: PageDao,
        private val ioExecutor: Executor
) {

    fun insert(pages: List<Page>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("WikiLocalCache", "inserting ${pages.size} pages")
            repoDao.insert(pages)
            insertFinished()
        }
    }


    fun pagesByTitle(name: String): DataSource.Factory<Int, Page> {
        val query = "%${name.replace(' ', '%')}%"
        return repoDao.pagesByTitle(query)
    }
}
