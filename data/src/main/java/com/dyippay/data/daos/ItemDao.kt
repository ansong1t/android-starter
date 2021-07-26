package com.dyippay.data.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dyippay.data.entities.AudioBook
import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.entities.Song
import com.dyippay.data.entities.ItemEntry
import com.dyippay.data.entities.SearchedItemEntry
import com.dyippay.data.entities.TvEpisode
import com.dyippay.data.entities.TvShow
import com.dyippay.data.mappers.ItemResponseToAudioBookMapper
import com.dyippay.data.mappers.ItemResponseToFeatureMovieMapper
import com.dyippay.data.mappers.ItemResponseToSongMapper
import com.dyippay.data.mappers.ItemResponseToTvEpisodeMapper
import com.dyippay.data.mappers.ItemResponseToTvShowMapper
import com.dyippay.data.responses.ItemResponse
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.data.resultentities.SearchedItemEntryWithDetails
import com.dyippay.data.types.ListItemType
import com.dyippay.data.types.responseToTrackType

@Dao
abstract class ItemDao : EntityDao<Song>() {

    @Transaction
    @Query("SELECT * FROM item_entries")
    abstract fun getPagedItems(): DataSource.Factory<Int, ItemEntryWithDetails>

    @Transaction
    @Query("SELECT * FROM searched_item_entries")
    abstract fun getPagedSearchedItems(): DataSource.Factory<Int, SearchedItemEntryWithDetails>

    @Query("DELETE FROM searched_item_entries")
    abstract suspend fun clearSearchResults()

    @Query("DELETE FROM item_entries")
    abstract suspend fun clearItemEntries()

    @Query("SELECT EXISTS (SELECT 1 FROM item_entries LIMIT 1)")
    abstract suspend fun hasItemEntry(): Boolean

    suspend fun insertItems(
        items: List<ItemResponse>,
        songMapper: ItemResponseToSongMapper,
        featureMovieMapper: ItemResponseToFeatureMovieMapper,
        tvEpisodeMapper: ItemResponseToTvEpisodeMapper,
        tvShowMapper: ItemResponseToTvShowMapper,
        audioBookMapper: ItemResponseToAudioBookMapper
    ) {
        val tvEpisodes = arrayListOf<TvEpisode>()
        items.forEach {
            when (responseToTrackType(it.kind)) {
                ListItemType.SONG -> insertSong(songMapper(it))
                ListItemType.FEATURE_MOVIE -> insertFeatureMovie(featureMovieMapper(it))
                ListItemType.TV_SHOW -> {
                    insertTvShow(tvShowMapper(it))
                    tvEpisodes.add(tvEpisodeMapper(it))
                }
                else -> {
                    if (it.wrapperType == "audiobook") {
                        insertAudioBook(audioBookMapper(it))
                    }
                }
            }
            val entry = ItemEntry(
                itemId = when (it.wrapperType) {
                    "track" -> when (responseToTrackType(it.kind)) {
                        ListItemType.TV_SHOW, ListItemType.AUDIOBOOK -> it.collectionId
                        else -> it.trackId
                    }
                    "audiobook" -> it.collectionId
                    else -> it.trackId
                },
                kind = when (it.wrapperType) {
                    "track" -> responseToTrackType(it.kind)
                    "audiobook" -> ListItemType.AUDIOBOOK
                    else -> ListItemType.UNKNOWN
                }
            )
            insertItemEntry(entry)
        }
        insertTvEpisodes(tvEpisodes)
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
