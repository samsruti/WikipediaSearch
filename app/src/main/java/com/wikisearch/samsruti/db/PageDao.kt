package com.wikisearch.samsruti.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wikisearch.samsruti.model.Page

/**
 * Room data access object for accessing the [Page] table.
 */
@Dao
interface PageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Page>)


    @Query("SELECT * FROM page WHERE (title LIKE :queryString) ORDER BY pageId DESC ")
    fun pagesByTitle(queryString: String): DataSource.Factory<Int,Page>
//
//    @Query("SELECT * FROM page WHERE pageId = :pageId")
//    fun pageDetailsFromId(pageId: BigInteger): Page
//NOte to pass data from one fragment to another
//    we can use ROom to use the data.
//    and also we can nav args (Safe Args) also
}


