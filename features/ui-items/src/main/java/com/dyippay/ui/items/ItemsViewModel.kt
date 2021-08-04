package com.dyippay.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.domain.interactors.UpdateItems
import com.dyippay.domain.observers.ObservePagedItems
import com.dyippay.util.ObservableLoadingCounter
import com.dyippay.util.collectInto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class ItemsViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedItems,
    private val interactor: UpdateItems
) : ViewModel() {

    private val loading = ObservableLoadingCounter()
    private val state: Flow<ItemsViewState> = loading.observable.map {
        ItemsViewState(isLoading = it)
    }

    val liveData: LiveData<ItemsViewState> = state.asLiveData()

    val pagedList: Flow<PagingData<ItemEntryWithDetails>>
        get() = pagingInteractor.observe()

    init {
        pagingInteractor(
            ObservePagedItems.Params(
                PAGING_CONFIG
            )
        )

        refresh(fromUser = false)
    }

    fun refresh(fromUser: Boolean = true) {
        viewModelScope.launch {
            interactor(UpdateItems.Params(UpdateItems.Page.REFRESH, fromUser))
                .collectInto(loading)
        }
    }

    companion object {
        private val PAGING_CONFIG = PagingConfig(
            pageSize = 60,
            prefetchDistance = 20,
            enablePlaceholders = false
        )
    }
}
