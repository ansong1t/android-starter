package com.dyippay.domain.observers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dyippay.data.daos.ItemDao
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.domain.PagingInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObservePagedItems @Inject constructor(
    private val itemDao: ItemDao
) : PagingInteractor<ObservePagedItems.Params, ItemEntryWithDetails>() {

    data class Params(
        override val pagingConfig: PagingConfig
    ) : Parameters<ItemEntryWithDetails>

    override fun createObservable(params: Params): Flow<PagingData<ItemEntryWithDetails>> =
        Pager(config = params.pagingConfig) {
            itemDao.getPagedItems()
        }.flow
}
