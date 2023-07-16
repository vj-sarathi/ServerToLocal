package com.vjsarathi.servertolocal.feature_room.utils

import com.vjsarathi.servertolocal.feature_room.data.NetWorkResponse
import retrofit2.HttpException
import java.io.IOException

object Extensions {

    fun <T> Throwable.handleException(): NetWorkResponse.Error<T> {
        return when (this) {
            is IOException -> {
                NetWorkResponse.Error(
                    message = Constants.CHECK_YOUR_INTERNET,
                    data = null
                )
            }

            is HttpException -> {
                NetWorkResponse.Error(
                    message = Constants.CHECK_YOUR_INTERNET,
                    data = null
                )
            }

            else -> {
                NetWorkResponse.Error(
                    message = Constants.NETWORK_ERROR,
                    data = null
                )
            }
        }
    }

}