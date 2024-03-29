package com.dyippay.data.repositories

import androidx.paging.PagingSource
import com.dyippay.data.daos.ItemDao
import com.dyippay.data.remotesources.GetItemRemoteSource
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val getItemRemoteSource: GetItemRemoteSource,
    private val itemDao: ItemDao
) : ItemRepository {

    override fun observePagedSearchedItems(): PagingSource<Int, SearchedItemEntryWithDetails> =
        itemDao.getPagedSearchedItems()

    override fun observePagedItems(): PagingSource<Int, ItemEntryWithDetails> =
        itemDao.getPagedItems()

    override suspend fun searchItems(searchKey: String) {
        // TODO
    }

    override suspend fun updateItems() {
        val result = getItemRemoteSource()
        val items = result.getOrThrow()
        itemDao.withTransaction {
            itemDao.insertItems(items)
        }
    }
}
