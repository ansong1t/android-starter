package com.dyippay.data.repositories.items

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.dyippay.data.daos.ItemDao
import com.dyippay.data.remotesources.GetItemRemoteSource
import com.dyippay.data.resultentities.ItemEntryWithDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

typealias ItemsStore = Store<String, List<ItemEntryWithDetails>>

@InstallIn(SingletonComponent::class)
@Module
object ItemsStoreModule {

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Provides
    @Singleton
    fun provideShowStore(
        getItemRemoteSource: GetItemRemoteSource,
        itemDao: ItemDao
    ): ItemsStore = StoreBuilder.from(
        fetcher = Fetcher.of { _: String ->
            getItemRemoteSource().getOrThrow()
        },
        sourceOfTruth = SourceOfTruth.of(
            reader = {
                itemDao.getAllItems()
            },
            writer = { _, response ->
                itemDao.insertItems(response)
            },
            delete = { itemDao.clearItemEntries() },
            deleteAll = { itemDao.clearItemEntries() }
        )
    ).build()
}
