package com.vjsarathi.servertolocal.feature_room.domain.remote.repository

import com.vjsarathi.servertolocal.feature_room.data.NetWorkResponse
import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.remote.model.RemoteUser
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface RemoteUserRepo {

    suspend fun getAllUsers(): Flow<NetWorkResponse<List<User>?>>

}