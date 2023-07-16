package com.vjsarathi.servertolocal.feature_room.data.remote.repository

import com.vjsarathi.servertolocal.feature_room.data.NetWorkResponse
import com.vjsarathi.servertolocal.feature_room.data.remote.data_source.RemoteApi
import com.vjsarathi.servertolocal.feature_room.domain.mapper.toUsers
import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.remote.repository.RemoteUserRepo
import com.vjsarathi.servertolocal.feature_room.utils.Constants.NETWORK_ERROR
import com.vjsarathi.servertolocal.feature_room.utils.Extensions.handleException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class RemoteUserRepoImpl(
    private val remoteApi: RemoteApi
) : RemoteUserRepo {
    override suspend fun getAllUsers(): Flow<NetWorkResponse<List<User>?>> =
        flow<NetWorkResponse<List<User>?>> {
            remoteApi.getAllUsers().also { response ->
                if (!response.isSuccessful) {
                    emit(NetWorkResponse.Error(message = NETWORK_ERROR, null))
                } else {
                    response.body()?.let { data ->
                        emit(NetWorkResponse.Success(data = data.toUsers()))
                    }
                }
            }
        }.catch {
            emit(it.handleException())
        }.onStart {
            emit(
                NetWorkResponse.Loading(isLoading = true)
            )
        }.onCompletion {
            emit(
                NetWorkResponse.Loading(isLoading = false)
            )
        }

}