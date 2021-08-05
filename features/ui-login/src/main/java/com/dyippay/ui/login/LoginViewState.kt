package com.dyippay.ui.login

import androidx.lifecycle.MutableLiveData
import com.dyippay.common.Event

data class LoginViewState(
    val buttonLoading: Boolean = true,
    val username: MutableLiveData<String> = MutableLiveData(""),
    val password: MutableLiveData<String> = MutableLiveData(""),
    val viewEvent: Event<LoginViewEvent>? = null
)

sealed class LoginViewEvent {
    object ProceedToHome : LoginViewEvent()
    data class Error(val message: String) : LoginViewEvent()
}
