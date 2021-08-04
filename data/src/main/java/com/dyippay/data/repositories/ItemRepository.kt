package com.dyippay.data.repositories

import androidx.paging.PagingSource
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails

interface ItemRepository {
    fun observePagedSearchedItems(): PagingSource<Int, SearchedItemEntryWithDetails>
    fun observePagedItems(): PagingSource<Int, ItemEntryWithDetails>
    suspend fun searchItems(searchKey: String = "")
    suspend fun updateItems()
}
