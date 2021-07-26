package com.dyippay.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.dyippay.data.entities.TvShow
import com.dyippay.data.resultentities.TvShowWithEpisodes
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TvShowDao : EntityDao<TvShow>() {

    @Query("SELECT * FROM tv_shows WHERE collection_id = :collectionId")
    abstract fun getTvShowWithEpisodes(collectionId: Long): Flow<TvShowWithEpisodes>
}
