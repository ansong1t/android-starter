package com.dyippay.data.repositories

import androidx.paging.DataSource
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails

interface ItemRepository {
    fun observePagedSearchedItems(): DataSource.Factory<Int, SearchedItemEntryWithDetails>
    fun observePagedItems(): DataSource.Factory<Int, ItemEntryWithDetails>
    suspend fun searchItems(searchKey: String = "")
    suspend fun updateItems()
}
