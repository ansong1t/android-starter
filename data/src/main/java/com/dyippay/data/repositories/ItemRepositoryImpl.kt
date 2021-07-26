package com.dyippay.data.repositories

import androidx.paging.DataSource
import com.dyippay.data.daos.ItemDao
import com.dyippay.data.datasources.GetItemDataSource
import com.dyippay.data.mappers.ItemResponseToAudioBookMapper
import com.dyippay.data.mappers.ItemResponseToFeatureMovieMapper
import com.dyippay.data.mappers.ItemResponseToSongMapper
import com.dyippay.data.mappers.ItemResponseToTvEpisodeMapper
import com.dyippay.data.mappers.ItemResponseToTvShowMapper
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val getItemDataSource: GetItemDataSource,
    private val itemDao: ItemDao,
    private val itemResponseToSongMapper: ItemResponseToSongMapper,
    private val itemResponseToFeatureMovieMapper: ItemResponseToFeatureMovieMapper,
    private val itemResponseToTvEpisodeMapper: ItemResponseToTvEpisodeMapper,
    private val itemResponseToTvShowMapper: ItemResponseToTvShowMapper,
    private val itemResponseToAudioBookMapper: ItemResponseToAudioBookMapper
) : ItemRepository {

    override fun observePagedSearchedItems(): DataSource.Factory<Int, SearchedItemEntryWithDetails> =
        itemDao.getPagedSearchedItems()

    override fun observePagedItems(): DataSource.Factory<Int, ItemEntryWithDetails> =
        itemDao.getPagedItems()

    override suspend fun searchItems(searchKey: String) {
        // TODO
    }

    override suspend fun updateItems() {
        val result = getItemDataSource()
        val items = result.getOrThrow()
        itemDao.withTransaction {
            itemDao.insertItems(
                items,
                itemResponseToSongMapper,
                itemResponseToFeatureMovieMapper,
                itemResponseToTvEpisodeMapper,
                itemResponseToTvShowMapper,
                itemResponseToAudioBookMapper
            )
        }
    }
}
