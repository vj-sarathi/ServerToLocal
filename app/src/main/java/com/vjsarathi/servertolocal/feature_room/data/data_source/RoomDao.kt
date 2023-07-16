package com.vjsarathi.servertolocal.feature_room.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vjsarathi.servertolocal.feature_room.domain.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface RoomDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: List<User>)
}