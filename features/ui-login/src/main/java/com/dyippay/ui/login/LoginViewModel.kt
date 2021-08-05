package com.dyippay.ui.login

import androidx.lifecycle.viewModelScope
import com.dyippay.ReduxViewModel
import com.dyippay.util.ObservableLoadingCounter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ReduxViewModel<LoginViewState>(
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
}
