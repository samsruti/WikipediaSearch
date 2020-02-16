package com.wikisearch.samsruti

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.wikisearch.samsruti.api.WikiApiService
import com.wikisearch.samsruti.db.PageDatabase
import com.wikisearch.samsruti.db.WikiLocalCache
import com.wikisearch.samsruti.repository.WikiRepository
import com.wikisearch.samsruti.ui.main.ViewModelFactory
import java.util.concurrent.Executors

/**
Dependecy Injection CUSTOM
 */
object DependencyInjection {

//    Todo Write Tests as Fake

    private fun provideCache(context: Context): WikiLocalCache {
        val database = PageDatabase.getInstance(context)
        return WikiLocalCache(database.getDao(), Executors.newSingleThreadExecutor())
    }


    private fun provideGithubRepository(context: Context): WikiRepository {
        return WikiRepository(WikiApiService.create(), provideCache(context))
    }


    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }
}
