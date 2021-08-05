package com.dyippay.ui.login

import androidx.lifecycle.viewModelScope
import com.dyippay.ReduxViewModel
import com.dyippay.base.InvokeError
import com.dyippay.base.InvokeSuccess
import com.dyippay.common.Event
import com.dyippay.domain.interactors.Login
import com.dyippay.util.ObservableLoadingCounter
import com.dyippay.util.collectInto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: Login
) : ReduxViewModel<LoginViewState>(
    LoginViewState()
) {

    private val buttonLoading = ObservableLoadingCounter()

    init {
        viewModelScope.launch {
            buttonLoading.observable.collect { active ->
                setState { copy(buttonLoading = active) }
            }
        }
    }

    fun login() {
        val username = currentState().username.value ?: ""
        val password = currentState().password.value ?: ""

        viewModelScope.launch {
            login(Login.Params(username, password))
                .collectInto(buttonLoading) { status ->
                    when (status) {
                        InvokeSuccess -> {
                            setState {
                                copy(viewEvent = Event(LoginViewEvent.ProceedToHome))
                            }
                        }
                        is InvokeError -> {
                            setState {
                                copy(viewEvent = Event(LoginViewEvent.Error(status.throwable.message!!)))
                            }
                        }
                    }
                }
        }
    }
}
