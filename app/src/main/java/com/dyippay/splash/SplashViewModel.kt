package com.dyippay.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyippay.common.Event
import com.dyippay.domain.interactors.IsLoggedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    isLoggedIn: IsLoggedIn
) : ViewModel() {

    private val _navState = MutableLiveData<Event<SplashState>>()
    val navState: LiveData<Event<SplashState>> = _navState

    init {
        viewModelScope.launch {
            isLoggedIn(IsLoggedIn.Params)
                .collect {
                    _navState.postValue(
                        Event(
                            if (it) SplashState.UserIsLoggedIn
                            else SplashState.UserIsNotLoggedIn
                        )
                    )
                }
        }
    }
}
