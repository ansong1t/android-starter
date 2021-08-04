package com.dyippay.ui.items

import com.dyippay.api.UiError

data class ItemsViewState(
    val isLoading: Boolean = false,
    val error: UiError? = null
)
