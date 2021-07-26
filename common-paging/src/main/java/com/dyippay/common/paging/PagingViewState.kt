package com.dyippay.common.paging

import com.dyippay.api.UiStatus

interface PagingViewState {
    val status: UiStatus
    val isLoaded: Boolean
}
