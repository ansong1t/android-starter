package com.dyippay.data.repositories

import com.dyippay.data.daos.TvShowDao
import com.dyippay.data.resultentities.TvShowWithEpisodes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val tvShowDao: TvShowDao
) : TvShowRepository {

    override fun observeTvShowAndEpisodes(collectionId: Long): Flow<TvShowWithEpisodes> {
        return tvShowDao.getTvShowWithEpisodes(collectionId)
    }
}
