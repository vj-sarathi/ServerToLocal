package com.vjsarathi.servertolocal.feature_room.data.remote.data_source

import com.vjsarathi.servertolocal.feature_room.domain.remote.model.RemoteUser
import retrofit2.Response
import retrofit2.http.GET


interface RemoteApi {

    @GET(allUsers)
    suspend fun getAllUsers(): Response<List<RemoteUser>?>

    companion object {
        const val BASE_URL = "https://crudcrud.com/api/2495fc7a188c427999583b2abd79699e/"
        const val allUsers = "allUsers"
    }
}