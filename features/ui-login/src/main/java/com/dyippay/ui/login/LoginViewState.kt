package com.dyippay.ui.login

import androidx.lifecycle.MutableLiveData

data class LoginViewState(
    val buttonLoading: Boolean = true,
    val mobileNumber: MutableLiveData<String> = MutableLiveData(""),
    val otp: MutableLiveData<String> = MutableLiveData("")
)
