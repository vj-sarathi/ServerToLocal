package com.vjsarathi.servertolocal.feature_room.presentation.rooms

import com.vjsarathi.servertolocal.feature_room.domain.model.User

data class UserListPageState(
    val usersList: List<User> = emptyList(),
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val gender: String = "",
    val isLoading: Boolean = false
)

sealed class UserListPageEvent {
    data class UsersList(val users: List<User>) : UserListPageEvent()
    data class Loading(val isLoading: Boolean = false) : UserListPageEvent()
}

sealed class EventChannel {
    object Idle : EventChannel()
    data class ErrorMsg(val message: String) : EventChannel()
    data class Loading(val isLoading: Boolean = false): EventChannel()
}