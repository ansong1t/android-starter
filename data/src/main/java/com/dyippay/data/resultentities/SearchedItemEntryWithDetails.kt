package com.dyippay.data.resultentities

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.dyippay.data.entities.Song
import com.dyippay.data.entities.SearchedItemEntry
import java.util.Objects

data class SearchedItemEntryWithDetails(
    @Embedded
    var searchItemEntry: SearchedItemEntry = SearchedItemEntry(),

    @Relation(
        parentColumn = "item_id",
        entityColumn = "track_id"
    )
    var song: Song = Song()
) {

    @Ignore
    fun generateStableId(): Long {
        return Objects.hash(Song::class.java.name, searchItemEntry.id).toLong()
    }
}
