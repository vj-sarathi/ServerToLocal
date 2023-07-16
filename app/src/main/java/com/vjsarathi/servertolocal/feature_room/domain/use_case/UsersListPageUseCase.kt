package com.vjsarathi.servertolocal.feature_room.domain.use_case

data class UsersListPageUseCase(
    val getAllUsers: GetAllUsersUseCase,
    val insertNewUser: InsertUser,
)
