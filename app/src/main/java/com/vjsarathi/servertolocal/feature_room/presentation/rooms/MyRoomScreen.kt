package com.vjsarathi.servertolocal.feature_room.presentation.rooms

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vjsarathi.servertolocal.R
import com.vjsarathi.servertolocal.feature_room.domain.model.User

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyRoomScreen(
    modifier: Modifier = Modifier,
    viewModel: UsersListViewModel = viewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val snackBarHostState = scaffoldState.snackbarHostState
    val uiState by viewModel.uiState.collectAsState()
    val channel = viewModel.eventChannel
    val snackBarState by viewModel.snackBarState.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState, modifier = Modifier.padding(16.dp))
        }
    ) {
        LaunchedEffect(key1 = snackBarState) {
            channel.collect { oneTimeEvent ->
                when (oneTimeEvent) {
                    is EventChannel.ErrorMsg -> {
                        scaffoldState.snackbarHostState.showSnackbar(oneTimeEvent.message)
                    }

                    EventChannel.Idle -> {}
                    is EventChannel.Loading -> {
                        viewModel.onEvent(UserListPageEvent.Loading(oneTimeEvent.isLoading))
                    }
                }
            }
        }
        if (uiState.isLoading)
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            }
        else {
            if (uiState.usersList.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No Users Found",
                        modifier = modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                ) {
                    items(uiState.usersList) { user ->
                        UserCard(user, modifier = modifier.padding(16.dp))
                    }

                }
            }
        }
    }
}


@Composable
fun UserCard(user: User, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.user_name, user.name),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.email, user.email),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.mobile, user.mobile),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.gender, user.gender),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



