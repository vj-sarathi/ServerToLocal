package com.vjsarathi.servertolocal.feature_room.domain.use_case

import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.repository.UserRepo

class InsertUser(
    private val userRepo: UserRepo,
) {
    suspend operator fun invoke(user: List<User>) {
        userRepo.insertUser(user = user)
    }
}