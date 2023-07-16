package com.vjsarathi.servertolocal.feature_room.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vjsarathi.servertolocal.feature_room.data.data_source.RoomDao
import com.vjsarathi.servertolocal.feature_room.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract val roomDao: RoomDao

    companion object {
        const val DATABASE_NAME = "users_db"
    }

}