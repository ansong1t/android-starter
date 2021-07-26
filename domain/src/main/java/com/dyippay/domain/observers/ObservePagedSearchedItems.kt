package com.dyippay.domain.observers

import androidx.paging.PagedList
import com.dyippay.data.FlowPagedListBuilder
import com.dyippay.data.repositories.ItemRepository
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails
import com.dyippay.domain.PagingInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObservePagedSearchedItems @Inject constructor(
    private val repository: ItemRepository
) : PagingInteractor<ObservePagedSearchedItems.Params, SearchedItemEntryWithDetails>() {

    data class Params(
        override val pagingConfig: PagedList.Config,
        override val boundaryCallback: PagedList.BoundaryCallback<SearchedItemEntryWithDetails>? = null
    ) : Parameters<SearchedItemEntryWithDetails>

    override fun createObservable(params: Params): Flow<PagedList<SearchedItemEntryWithDetails>> {
        return FlowPagedListBuilder(
            repository.observePagedSearchedItems(),
            params.pagingConfig,
            boundaryCallback = params.boundaryCallback
        ).buildFlow()
    }
}
