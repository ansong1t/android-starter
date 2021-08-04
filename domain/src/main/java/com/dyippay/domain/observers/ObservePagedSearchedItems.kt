package com.dyippay.domain.observers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dyippay.data.daos.ItemDao
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails
import com.dyippay.domain.PagingInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObservePagedSearchedItems @Inject constructor(
    private val itemDao: ItemDao
) : PagingInteractor<ObservePagedSearchedItems.Params, SearchedItemEntryWithDetails>() {

    data class Params(
        override val pagingConfig: PagingConfig
    ) : Parameters<SearchedItemEntryWithDetails>

    override fun createObservable(params: Params): Flow<PagingData<SearchedItemEntryWithDetails>> =
        Pager(config = params.pagingConfig) {
            itemDao.getPagedSearchedItems()
        }.flow
}
