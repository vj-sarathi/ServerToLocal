package com.vjsarathi.servertolocal.feature_room.presentation.rooms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjsarathi.servertolocal.feature_room.data.NetWorkResponse
import com.vjsarathi.servertolocal.feature_room.domain.model.User
import com.vjsarathi.servertolocal.feature_room.domain.remote.use_case.GetAllUsersFromServer
import com.vjsarathi.servertolocal.feature_room.domain.use_case.UsersListPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val usersListPageUseCase: UsersListPageUseCase,
    private val getAllUsersFromServer: GetAllUsersFromServer
) : ViewModel() {

    private val _snackBarState = MutableStateFlow(false)
    val snackBarState = _snackBarState.asStateFlow()

    private val _uiState = MutableStateFlow(UserListPageState())
    val uiState = _uiState.asStateFlow()

    private val _eventChannel = Channel<EventChannel>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private var getAllRooms: Job? = null

    init {
        getAllUsers()
    }

    fun onEvent(event: UserListPageEvent) {
        when (event) {
            is UserListPageEvent.UsersList -> {
                _uiState.update {
                    it.copy(
                        usersList = event.users
                    )
                }
            }

            is UserListPageEvent.Loading -> {
                _uiState.update {
                    it.copy(
                        isLoading = event.isLoading
                    )
                }
            }
        }
    }

    private fun getAllUsers() {
        getAllRooms?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            getAllUsersFromServer().collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is NetWorkResponse.Error -> {
                            result.message?.let {
                                _eventChannel.send(EventChannel.ErrorMsg(it))
                            }
                        }

                        is NetWorkResponse.Loading -> {
                            _eventChannel.send(EventChannel.Loading(result.loading))
                        }

                        is NetWorkResponse.Success -> {
                            _eventChannel.send(EventChannel.Loading(result.loading))
                            result.data?.let {
                                insertToLocalDB(it)
                            }
                            getAllUsersFromLocalDB()
                        }
                    }
                }
            }
        }
    }

    private suspend fun getAllUsersFromLocalDB() {
        usersListPageUseCase.getAllUsers().collect {
            Log.d("User List", "Users list - $it")
            onEvent(UserListPageEvent.UsersList(it))
        }
    }

    private suspend fun insertToLocalDB(it: List<User>) {
        usersListPageUseCase.insertNewUser(it)
    }

}