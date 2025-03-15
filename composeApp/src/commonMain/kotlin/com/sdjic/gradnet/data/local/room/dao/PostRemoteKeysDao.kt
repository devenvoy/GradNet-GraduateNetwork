package com.sdjic.gradnet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sdjic.gradnet.data.local.entity.PostRemoteKeys

@Dao
interface PostRemoteKeysDao {

    @Query("SELECT * FROM post_remote_keys WHERE id =:id")
    suspend fun getRemoteKeys(id: String): PostRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PostRemoteKeys>)

    @Query("DELETE FROM post_remote_keys")
    suspend fun deleteAllRemoteKeys()

    @Transaction
    suspend fun deleteAndInsertKeys(remoteKeys: List<PostRemoteKeys>) {
        deleteAllRemoteKeys()
        addAllRemoteKeys(remoteKeys)
    }

}