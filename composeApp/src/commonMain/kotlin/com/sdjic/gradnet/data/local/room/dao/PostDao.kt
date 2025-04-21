package com.sdjic.gradnet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.cash.paging.PagingSource
import com.sdjic.gradnet.data.network.entity.dto.PostTable

@Dao
interface PostDao {

 /*   @Query("SELECT * FROM post_table")
    fun getPosts(): PagingSource<Int, PostTable>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(images: List<PostTable>)

    @Query("DELETE FROM post_table")
    suspend fun removeAllPosts()

    @Transaction
    suspend fun deleteAndInsertImages(images: List<PostTable>) {
        removeAllPosts()
        insertPosts(images)
    }
}