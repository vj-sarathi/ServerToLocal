package com.vjsarathi.servertolocal.feature_room.domain.remote.use_case

import com.vjsarathi.servertolocal.feature_room.data.NetWorkResponse
import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.remote.model.RemoteUser
import com.vjsarathi.servertolocal.feature_room.domain.remote.repository.RemoteUserRepo
import kotlinx.coroutines.flow.Flow

class GetAllUsersFromServer(
    private val remoteUserRepo: RemoteUserRepo,
) {

    suspend operator fun invoke(): Flow<NetWorkResponse<List<User>?>> {
        return remoteUserRepo.getAllUsers()
    }

}