package com.dyippay.ui.items

import com.dyippay.api.UiIdle
import com.dyippay.api.UiStatus
import com.dyippay.common.paging.PagingViewState

data class ItemsViewState(
    val isEmpty: Boolean = false,
    override val status: UiStatus = UiIdle,
    override val isLoaded: Boolean = false
) : PagingViewState
