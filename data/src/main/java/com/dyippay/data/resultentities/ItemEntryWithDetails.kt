package com.dyippay.data.resultentities

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.dyippay.data.entities.AudioBook
import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.entities.Song
import com.dyippay.data.entities.ItemEntry
import com.dyippay.data.entities.TvShow
import com.dyippay.data.types.ListItemType
import java.util.Objects

data class ItemEntryWithDetails(
    @Embedded
    var itemEntry: ItemEntry = ItemEntry(),

    @Relation(
        parentColumn = "item_id",
        entityColumn = "track_id"
    )
    var song: Song? = Song(),

    @Relation(
        parentColumn = "item_id",
        entityColumn = "track_id"
    )
    var featureMovie: FeatureMovie? = null,

    @Relation(
        parentColumn = "item_id",
        entityColumn = "collection_id"
    )
    var tvShow: TvShow? = null,

    @Relation(
        parentColumn = "item_id",
        entityColumn = "collection_id"
    )
    var audioBook: AudioBook? = null
) {

    @Ignore
    fun generateStableId(): Long {
        return Objects.hash(Song::class.java.name, FeatureMovie::class.java.name, itemEntry.itemId)
            .toLong()
    }

    @Ignore
    fun trackName(): String = when (itemEntry.kind) {
        ListItemType.SONG -> song!!.trackName
        ListItemType.FEATURE_MOVIE -> featureMovie!!.trackName
        ListItemType.TV_SHOW -> tvShow!!.collectionName
        ListItemType.AUDIOBOOK -> audioBook!!.collectionName
        else -> "No track Name"
    }

    @Ignore
    fun genre(): String = when (itemEntry.kind) {
        ListItemType.SONG -> song!!.primaryGenreName
        ListItemType.FEATURE_MOVIE -> featureMovie!!.primaryGenreName
        ListItemType.TV_SHOW -> tvShow!!.primaryGenreName
        ListItemType.AUDIOBOOK -> audioBook!!.primaryGenreName
        else -> "No Genre"
    }

    @Ignore
    fun imageUrl(): String = when (itemEntry.kind) {
        ListItemType.SONG -> song!!.artworkUrl100
        ListItemType.FEATURE_MOVIE -> featureMovie!!.artworkUrl100
        ListItemType.TV_SHOW -> tvShow!!.artworkUrl100
        ListItemType.AUDIOBOOK -> audioBook!!.artworkUrl100
        else -> ""
    }

    @Ignore
    fun trackPrice(): Double = when (itemEntry.kind) {
        ListItemType.SONG -> song!!.trackPrice
        ListItemType.FEATURE_MOVIE -> featureMovie!!.trackPrice
        ListItemType.TV_SHOW -> tvShow!!.collectionPrice
        ListItemType.AUDIOBOOK -> audioBook!!.collectionPrice
        else -> 0.0
    }

    @Ignore
    fun currency(): String = when (itemEntry.kind) {
        ListItemType.SONG -> song!!.currency
        ListItemType.FEATURE_MOVIE -> featureMovie!!.currency
        ListItemType.TV_SHOW -> tvShow!!.currency
        ListItemType.AUDIOBOOK -> audioBook!!.currency
        else -> ""
    }

    @Ignore
    fun kind(): String = itemEntry.kind.type
}
