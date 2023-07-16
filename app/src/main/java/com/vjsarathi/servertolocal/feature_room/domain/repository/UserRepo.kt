package com.vjsarathi.servertolocal.feature_room.domain.repository

import com.vjsarathi.servertolocal.feature_room.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getAllUsers() : Flow<List<User>>

    suspend fun insertUser(user: List<User>)
}