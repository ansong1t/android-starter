package com.dyippay.domain.observers

import androidx.paging.PagedList
import com.dyippay.data.FlowPagedListBuilder
import com.dyippay.data.repositories.ItemRepository
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.domain.PagingInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObservePagedItems @Inject constructor(
    private val repository: ItemRepository
) : PagingInteractor<ObservePagedItems.Params, ItemEntryWithDetails>() {

    data class Params(
        override val pagingConfig: PagedList.Config,
        override val boundaryCallback: PagedList.BoundaryCallback<ItemEntryWithDetails>? = null
    ) : Parameters<ItemEntryWithDetails>

    override fun createObservable(params: Params): Flow<PagedList<ItemEntryWithDetails>> {
        return FlowPagedListBuilder(
            repository.observePagedItems(),
            params.pagingConfig,
            boundaryCallback = params.boundaryCallback
        ).buildFlow()
    }
}
