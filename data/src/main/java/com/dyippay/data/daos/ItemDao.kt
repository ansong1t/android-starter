package com.dyippay.data.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dyippay.data.remotesources.AllItems
import com.dyippay.data.entities.AudioBook
import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.entities.ItemEntry
import com.dyippay.data.entities.SearchedItemEntry
import com.dyippay.data.entities.Song
import com.dyippay.data.entities.TvEpisode
import com.dyippay.data.entities.TvShow
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ItemDao : EntityDao<Song>() {

    @Transaction
    @Query("SELECT * FROM item_entries")
    abstract fun getPagedItems(): PagingSource<Int, ItemEntryWithDetails>

    @Transaction
    @Query("SELECT * FROM item_entries")
    abstract fun getAllItems(): Flow<List<ItemEntryWithDetails>>

    @Transaction
    @Query("SELECT * FROM searched_item_entries")
    abstract fun getPagedSearchedItems(): PagingSource<Int, SearchedItemEntryWithDetails>

    @Query("DELETE FROM searched_item_entries")
    abstract suspend fun clearSearchResults()

    @Query("DELETE FROM item_entries")
    abstract suspend fun clearItemEntries()

    @Query("SELECT EXISTS (SELECT 1 FROM item_entries LIMIT 1)")
    abstract suspend fun hasItemEntry(): Boolean

    suspend fun insertItems(
        allItems: AllItems
    ) {
        allItems.songs.forEach { insertSong(it) }
        allItems.featureMovies.forEach { insertFeatureMovie(it) }
        allItems.tvShows.forEach { insertTvShow(it) }
        allItems.audioBooks.forEach { insertAudioBook(it) }
        allItems.entries.forEach { insertItemEntry(it) }
        insertTvEpisodes(allItems.tvEpisodes)
    }

    @Insert(entity = SearchedItemEntry::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSearchedItemEntry(entry: SearchedItemEntry)

    @Insert(entity = ItemEntry::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertItemEntry(entry: ItemEntry)

    @Insert(entity = Song::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSong(song: Song): Long

    @Insert(entity = FeatureMovie::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFeatureMovie(featureMovie: FeatureMovie): Long

    @Insert(entity = TvEpisode::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTvEpisodes(tvEpisodes: List<TvEpisode>)

    @Insert(entity = TvShow::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTvShow(show: TvShow): Long

    @Insert(entity = AudioBook::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAudioBook(audioBook: AudioBook): Long
}
