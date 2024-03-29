package com.dyippay.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dyippay.data.converters.ItemConverter
import com.dyippay.data.converters.UserDBConverters
import com.dyippay.data.entities.AudioBook
import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.entities.ItemEntry
import com.dyippay.data.entities.SearchedItemEntry
import com.dyippay.data.entities.Song
import com.dyippay.data.entities.TvEpisode
import com.dyippay.data.entities.TvShow
import com.dyippay.data.entities.session.token.AccessToken
import com.dyippay.data.entities.session.user.User
import dev.matrix.roomigrant.GenerateRoomMigrations

@Database(
    entities = [
        AudioBook::class,
        Song::class,
        FeatureMovie::class,
        TvShow::class,
        TvEpisode::class,
        ItemEntry::class,
        SearchedItemEntry::class,
        AccessToken::class,
        User::class
    ],
    version = 1
)
@TypeConverters(ItemConverter::class, UserDBConverters::class)
@GenerateRoomMigrations
abstract class DyippayRoomDatabase : RoomDatabase(), DyippayDatabase
