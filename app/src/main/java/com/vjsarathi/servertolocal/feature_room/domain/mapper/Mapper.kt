package com.vjsarathi.servertolocal.feature_room.domain.mapper

import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.remote.model.RemoteUser

fun List<RemoteUser>.toUsers() = this.map {
    User(
        id = it._id,
        name = it.name,
        email = it.email,
        mobile = it.mobile,
        gender = it.gender
    )
}