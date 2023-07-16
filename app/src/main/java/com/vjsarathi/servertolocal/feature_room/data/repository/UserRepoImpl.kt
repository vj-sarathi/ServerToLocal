package com.vjsarathi.servertolocal.feature_room.data.repository

import com.vjsarathi.servertolocal.feature_room.data.data_source.RoomDao
import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.repository.UserRepo
import kotlinx.coroutines.flow.Flow

class UserRepoImpl(
    private val roomDao: RoomDao
) : UserRepo {

    override fun getAllUsers(): Flow<List<User>> {
        return roomDao.getAllUsers()
    }

    override suspend fun insertUser(user: List<User>) {
        roomDao.insertRoom(room = user)
    }
}