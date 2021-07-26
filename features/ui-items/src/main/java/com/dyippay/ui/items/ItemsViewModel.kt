package com.dyippay.ui.items

import com.dyippay.api.UiStatus
import com.dyippay.base.InvokeStatus
import com.dyippay.common.paging.PagingViewModel
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.domain.interactors.UpdateItems
import com.dyippay.domain.observers.ObservePagedItems
import com.dyippay.util.AppCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class ItemsViewModel @Inject constructor(
    override val dispatchers: AppCoroutineDispatchers,
    override val pagingInteractor: ObservePagedItems,
    private val interactor: UpdateItems
) : PagingViewModel<ItemsViewState, ItemEntryWithDetails, ObservePagedItems>(
    state = ItemsViewState(),
    pageSize = 6
) {

    init {
        pagingInteractor(
            ObservePagedItems.Params(
                pageListConfig,
                boundaryCallback
            )
        )

        launchObserves()

        refresh(fromUser = false, fullRefresh = false)
    }

    override fun callRefresh(fromUser: Boolean): Flow<InvokeStatus> {
        return interactor(UpdateItems.Params(UpdateItems.Page.REFRESH, fromUser))
    }

    override fun callLoadMore(): Flow<InvokeStatus> {
        return interactor(UpdateItems.Params(UpdateItems.Page.NEXT_PAGE))
    }

    override fun ItemsViewState.setLoaded(isLoaded: Boolean): ItemsViewState =
        copy(isLoaded = isLoaded)

    override fun ItemsViewState.setStatus(status: UiStatus): ItemsViewState =
        copy(status = status)
}
