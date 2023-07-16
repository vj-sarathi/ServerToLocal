package com.vjsarathi.servertolocal.feature_room.data

sealed class NetWorkResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val loading: Boolean = false
) {

    class Success<T>(data: T) : NetWorkResponse<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetWorkResponse<T>(data, message)

    class Loading<T>(isLoading: Boolean) : NetWorkResponse<T>(loading = isLoading)

}
