package com.dyippay.data.repositories

import com.dyippay.data.resultentities.TvShowWithEpisodes
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun observeTvShowAndEpisodes(collectionId: Long): Flow<TvShowWithEpisodes>
}
