package com.simplekjl.servers.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplekjl.domain.model.ServerDetails
import com.simplekjl.domain.repository.SessionManager
import com.simplekjl.domain.usecase.GetAllServersUseCase
import com.simplekjl.domain.utils.Result.Error
import com.simplekjl.domain.utils.Result.Success
import com.simplekjl.servers.navigation.NavTarget.Login
import com.simplekjl.servers.navigation.Navigator
import com.simplekjl.servers.ui.list.ServerListState.FetchingData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServerListViewModel(
    private val getAllServersUseCase: GetAllServersUseCase,
    private val sessionManager: SessionManager,
    private val navigator: Navigator,
) : ViewModel() {

    private val _serverListState =
        MutableStateFlow<ServerListState>(FetchingData)
    val serverListState: StateFlow<ServerListState> = _serverListState

    private val _serverList = MutableStateFlow<List<ServerDetails>>(listOf())
    val serverList: StateFlow<List<ServerDetails>> = _serverList

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        viewModelScope.launch {
            delay(2000L)
            getServerList()
        }
    }

    fun logout() {
        sessionManager.deleteAuthToken()
        navigator.navigateTo(Login)
    }

    fun swipeRefresh() {
        _isRefreshing.value = true
        getServerList()
    }

    private fun getServerList() {
        _serverListState.value = ServerListState.FetchingData
        viewModelScope.launch {
            when (val serversResult = getAllServersUseCase(Unit)) {
                is Error -> {
                    _isRefreshing.value = false
                }
                is Success -> {
                    _isRefreshing.value = false
                    _serverList.value = serversResult.data
                }
            }
        }
    }
}

sealed class ServerListState {
    object FetchingData : ServerListState()
    data class LoadData(val serverList: List<ServerDetails>) : ServerListState()
    object Error : ServerListState()
}