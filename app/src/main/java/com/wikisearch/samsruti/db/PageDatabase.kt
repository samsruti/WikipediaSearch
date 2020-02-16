
package com.wikisearch.samsruti.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.wikisearch.samsruti.model.Page

/**
 * Database schema that holds the list of pages.
 */
@Database(
        entities = [Page::class],
        version = 1,
        exportSchema = false
)
abstract class PageDatabase : RoomDatabase() {

    abstract fun getDao(): PageDao

    companion object {

        @Volatile
        private var INSTANCE: PageDatabase? = null

        fun getInstance(context: Context): PageDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        PageDatabase::class.java, "Wiki.db")
                        .build()
    }
}
