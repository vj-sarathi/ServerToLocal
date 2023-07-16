package com.vjsarathi.servertolocal.feature_room.domain.use_case

import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.repository.UserRepo
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(
    private val userRepo: UserRepo,
) {

    operator fun invoke(): Flow<List<User>> {
        return userRepo.getAllUsers()
    }

}