package com.simplekjl.servers.ui.login

import androidx.lifecycle.ViewModel
import com.simplekjl.domain.repository.ServersRepository
import com.simplekjl.domain.repository.SessionManager
import com.simplekjl.servers.navigation.Navigator
import com.simplekjl.servers.ui.login.LoginState.NotLoggedIn
import com.simplekjl.servers.ui.login.LoginState.SignedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val navigator: Navigator,
    private val repository: ServersRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _loginState = MutableStateFlow(if (isLoggedIn()) SignedIn else NotLoggedIn)
    val loginState: StateFlow<LoginState> = _loginState

    private fun isLoggedIn(): Boolean {
        val token = sessionManager.fetchAuthToken()
        return token.isNotBlank()
    }
}

sealed class LoginState {
    object NotLoggedIn : LoginState()
    object Loading : LoginState()
    object SignedIn : LoginState()
    object Error : LoginState()
}